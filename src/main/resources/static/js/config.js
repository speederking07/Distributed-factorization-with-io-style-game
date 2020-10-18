$( document ).ready(function () {
    $('#colorInput').change(function () {
        let color = Color.fromHex($(this).val());
        let label = $('#colorLabel');
        label.css('background-color', color.get());
        label.css('box-shadow', color.darken().get()+" 2px 2px")
    });

    $('#saveConfig').click(function (){
        $('#configDiv').attr('visible', 'False');
        $('#configFBtn').removeAttr('active');
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
        $('.extended label[row="'+row+'"][col="'+col+'"]').css('background-color', c1);
    });
}