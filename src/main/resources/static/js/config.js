/**
 * Code of handling changing settings
 *
 * @author Marek Bauer
 */

$(document).ready(function () {
    $('#colorInput').change(function () {
        let color = Color.fromHex($(this).val());
        let label = $('#colorLabel');
        label.css('background-color', color.get());
        label.css('box-shadow', color.darken().get() + " 2px 2px")
    });

    $('#colorLabel').click(event => {
        event.preventDefault();
        openColorPicker($('#colorInput'))
    });

    $('#cloneColorBtn').click(() => {
        setAllPatternColor($('#colorInput').val())
    });

    $('#saveConfig').click(function () {
        $('#configDiv').attr('visible', 'False');
        $('#configFBtn').removeAttr('active');
        $('.colorPickerDiv').remove();
        const settings = {
            namesAbove: $('#namesBox').is(':checked'),
            boardAnimation: $('#boardAnimationsBox').is(':checked'),
            dyingAnimation: $('#killingAnimationsBox').is(':checked'),
            colorsInCSV: currentConfigToPattern().toJSON(Color.fromHex($('#colorInput').val())),
        };
        updateSettings(settings);
    });

    $('#quitConfig').click(function () {
        $('#configDiv').attr('visible', 'False');
        $('#configFBtn').removeAttr('active');
        $('.colorPickerDiv').remove();
        refreshSettings();
    });

    $('#changeGraphicalSettings').click(function () {
        updateDisplay();

        $('#settingDiv').attr('visible', 'False');
        $('#settingFBtn').removeAttr('active');

        const settings = {
            namesAbove: $('#namesBox').is(':checked'),
            boardAnimation: $('#boardAnimationsBox').is(':checked'),
            dyingAnimation: $('#killingAnimationsBox').is(':checked'),
            colorsInCSV: currentConfigToPattern().toJSON(Color.fromHex($('#colorInput').val())),
        };
        updateSettings(settings);
    });

    generatePattern();
    refreshSettings();
});

/**
 * Function downloading user setting from server and updating then
 */
function refreshSettings() {
    $.get({
        url: "/user/settings",
        dataType: "json",
        success: function (data) {
            pattern = Pattern.FromJSON(data.colorsInCSV);
            color = Color.formJson(data.colorsInCSV);
            if (data.namesAbove) {
                $('#namesBox').attr("checked", "");
            } else {
                $('#namesBox').removeAttr("checked");
            }
            if (data.boardAnimation) {
                $('#boardAnimationsBox').attr("checked", "");
            } else {
                $('#boardAnimationsBox').removeAttr("checked");
            }
            if (data.dyingAnimation) {
                $('#killingAnimationsBox').attr("checked", "");
            } else {
                $('#killingAnimationsBox').removeAttr("checked");
            }
            loadPattern(pattern);
            updateDisplay();
        }
    });
}

/**
 * Function setting new display preferences by checkin form
 */
function updateDisplay() {
    $("#colorInput").val(color.toHex());
    $("#colorInput").change();
    let config = [$('#namesBox').is(':checked'),
        $('#boardAnimationsBox').is(':checked'),
        $('#killingAnimationsBox').is(':checked')];
    BoardView.prototype.getGraphicalSettings = () => config;
    if (demo instanceof Demo) {
        demo.view.draw = demo.view.generateDrawFunction(config[0], config[1], config[2]);
    }
}

/**
 * Function uploading current setting to server
 * @param settings - current settings abzect
 */
function updateSettings(settings) {
    //settings = JSON.stringify({namesAbove:true, boardAnimation:true, dyingAnimation:true, colorsInCSV:"#010000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n"})
    const s = settings;
    const toSent = JSON.stringify(settings);
    $.post({
        url: "/user/settings",
        contentType: "application/json; charset=utf-8",
        data: toSent,
        success: function () {
        },
        error: function (data) {
            popup('Error', data.responseText, [['Never mind', () => null], ["Try again", () => updateSettings(s)]]);
        }
    });
}

/**
 * Function generating changing pattern form
 */
function generatePattern() {
    let patDiv = '';
    const a = [3, 4, 5, 0, 1, 2, 3, 4, 5, 0, 1, 2];
    for (let x = 0; x < 3; x++) {
        patDiv += "<div class='extended'>";
        for (let y = 0; y < 12; y++) {
            patDiv += '<label class="patternBox" for="p' + a[x] + a[y] + '" col="' + a[x] + '" row="' + a[y] + '"></label>'
        }
        patDiv += "</div>";
    }
    for (let x = 3; x < 9; x++) {
        patDiv += "<div>";
        for (let y = 0; y < 12; y++) {
            if (y === 0 || y === 9) patDiv += "<span class='extended'>";
            patDiv += '<label class="patternBox" for="p' + a[x] + a[y] + '" col="' + a[x] + '" row="' + a[y] + '"></label>';
            if (y === 2 || y === 11) patDiv += "</span>";
        }
        patDiv += "</div>";
    }
    for (let x = 9; x < 12; x++) {
        patDiv += "<div class='extended'>";
        for (let y = 0; y < 12; y++) {
            patDiv += '<label class="patternBox" for="p' + a[x] + a[y] + '" col="' + a[x] + '" row="' + a[y] + '"></label>';
        }
        patDiv += "</div>";
    }
    for (let x = 0; x < 6; x++) {
        for (let y = 0; y < 6; y++) {
            patDiv += '<input type="color" id="p' + x + y + '" col="' + x + '" row="' + y + '">'
        }
    }
    $('#choosePatternDiv').html(patDiv);

    $('#choosePatternDiv input[type="color"]').change(function () {
        let col = $(this).attr('col');
        let row = $(this).attr('row');
        let color = Color.fromHex($(this).val());
        let c1 = color.get();
        let c2 = color.lighten().get();
        $('label[row="' + row + '"][col="' + col + '"]').css('background-color', c1);
        $('.extended label[row="' + row + '"][col="' + col + '"]').css('background-color', c2);
    });

    setAllPatternColor('#ff0000');

    $('#choosePatternDiv label').click(event => {
        event.preventDefault();
        openColorPicker($('#' + $(event.target).attr('for')));
    });
}

/**
 * Setting pattern choosing form to one color
 * @param color{Color} - color to set to
 */
function setAllPatternColor(color) {
    $('#choosePatternDiv input[type=color]').each((_, target) => {
        $(target).val(color);
        $(target).change();
    });
}

/**
 * Setting pattern choosing form to pattern
 * @param pattern{Pattern} - pattern to set to
 */
function loadPattern(pattern) {
    for (const x of Array(6).keys()) {
        for (const y of Array(6).keys()) {
            const handler = $('#p' + x + y);
            handler.val(pattern.getColor(x, y).toHex());
            handler.change()
        }
    }
}

/**
 * Function opening color picker form if it is possible
 *
 * @param input - handler of input to save data to
 * @returns {boolean} - if was onend
 */
function openColorPicker(input) {
    if ($('.colorPickerDiv').length === 0) {
        $('body').append("<div class='colorPickerDiv floatingDiv'>" +
            "<h1>Choose color</h1>" +
            "<div class=\"wheel\" id=\"colorWheel\"></div>" +
            "<input type='button' id='confirmColor' value='Confirm'>" +
            "</div>");
        let color = input.val();
        let colorWheel = new iro.ColorPicker("#colorWheel", {
            layout: [
                {
                    component: iro.ui.Wheel,
                    options: {
                        wheelLightness: true,
                        wheelAngle: 0,
                        wheelDirection: "anticlockwise"
                    }
                }],
            color: color,
        });
        $('#confirmColor').click(() => {
            input.val(colorWheel.color.hexString);
            input.change();
            $('.colorPickerDiv').remove();
        });
        return true;
    } else {
        return false;
    }
}

/**
 * Converting current pattern from pattern selection form to pattern object
 * @returns {Pattern}
 */
function currentConfigToPattern() {
    let pattern = [];
    for (let x = 0; x < 6; x++) {
        pattern[x] = [];
        for (let y = 0; y < 6; y++) {
            pattern[x][y] = Color.fromHex($('#p' + y + x).val());
        }
    }
    return new Pattern(pattern)
}