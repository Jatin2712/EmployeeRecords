/**
 * 
 */
document.getElementById('select-all').onclick = function() {
    var checkboxes = document.getElementsByName('vehicle');
    for (var checkbox of checkboxes) {
        checkbox.checked = this.checked;
    }
}

function stringlength(inputtxt, minlength, maxlength)
{ 
var field = inputtxt.value; 
var mnlen = minlength;
var mxlen = maxlength;

if(field.length<mnlen || field.length> mxlen)
{ 
alert("Please input the userid between " +mnlen+ " and " +mxlen+ " characters");
return false;
}
else
{ 
alert('Your userid have accepted.');
return true;
}
}