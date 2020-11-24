$( document ).ready(function () {
    $('#colorInput').change(function () {
        let color = Color.fromHex($(this).val());
        let label = $('#colorLabel');
        label.css('background-color', color.get());
        label.css('box-shadow', color.darken().get()+" 2px 2px")
    });

    $('#colorLabel').click(event =>{
        event.preventDefault();
        openColorPicker($('#colorInput'))
    });

    $('#cloneColorBtn').click(()=>{
        setAllPatternColor($('#colorInput').val())
    });

    $('#saveConfig').click(function (){
        $('#configDiv').attr('visible', 'False');
        $('#configFBtn').removeAttr('active');
        $('.colorPickerDiv').remove();
    });

    $('#changeGraphicalSettings').click(function (){
        let config = [$('#namesBox').is(':checked'),
            $('#boardAnimationsBox').is(':checked'),
            $('#killingAnimationsBox').is(':checked')];
        BoardView.prototype.getGraphicalSettings = () => config;
        if(demo instanceof Demo){
            demo.view.draw = demo.view.generateDrawFunction(config[0], config[1], config[2]);
        }
        $('#settingDiv').attr('visible', 'False');
        $('#settingFBtn').removeAttr('active');
    });

    generatePattern();
});

function generatePattern() {
    let patDiv = '';
    const a = [3,4,5,0,1,2,3,4,5,0,1,2];
    for(let x = 0; x < 3; x++){
        patDiv += "<div class='extended'>";
        for(let y = 0; y < 12; y++){
            patDiv += '<label class="patternBox" for="p'+a[x]+a[y]+'" col="'+a[x]+'" row="'+a[y]+'"></label>'
        }
        patDiv += "</div>";
    }
    for(let x = 3; x < 9; x++){
        patDiv += "<div>";
        for(let y = 0; y < 12; y++){
            if(y === 0 || y===9) patDiv += "<span class='extended'>";
            patDiv += '<label class="patternBox" for="p'+a[x]+a[y]+'" col="'+a[x]+'" row="'+a[y]+'"></label>';
            if(y === 2 || y===11) patDiv += "</span>";
        }
        patDiv += "</div>";
    }
    for(let x = 9; x < 12; x++){
        patDiv += "<div class='extended'>";
        for(let y = 0; y < 12; y++){
            patDiv += '<label class="patternBox" for="p'+a[x]+a[y]+'" col="'+a[x]+'" row="'+a[y]+'"></label>';
        }
        patDiv += "</div>";
    }
    for(let x = 0; x < 6; x++){
        for(let y = 0; y < 6; y++){
            patDiv += '<input type="color" id="p'+x+y+'" col="'+x+'" row="'+y+'">'
        }
    }
    $('#choosePatternDiv').html(patDiv);

    $('#choosePatternDiv input[type="color"]').change(function () {
        let col = $(this).attr('col');
        let row = $(this).attr('row');
        let color = Color.fromHex($(this).val());
        let c1 = color.get();
        let c2 = color.lighten().get();
        $('label[row="'+row+'"][col="'+col+'"]').css('background-color', c1);
        $('.extended label[row="'+row+'"][col="'+col+'"]').css('background-color', c2);
    });

    setAllPatternColor('#ff0000');

    $('#choosePatternDiv label').click(event =>{
        event.preventDefault();
        openColorPicker($('#' + $(event.target).attr('for')));
    });
}

function setAllPatternColor(color) {
    $('#choosePatternDiv input[type=color]').each((_, target) =>{
        $(target).val(color);
        $(target).change();
    });
}

function openColorPicker(input){
    if($('.colorPickerDiv').length === 0) {
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
    }
    else{
        return false;
    }
}

function currentConfigToPattern() {
    let pattern = [];
    for(let x = 0; x < 6; x++){
        pattern[x] = [];
        for(let y = 0; y < 6; y++){
            pattern[x][y] = Color.fromHex($('#p'+x+y).val());
        }
    }
    return new Pattern(pattern)
}