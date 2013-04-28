/** Global item repository for Dmp js lib */
if(!Dmp)
{
	var Dmp = {};
}

Dmp.toggleOnSelectChange = function(select, valueHide, blockHide, inputClean){
	if(select.item(select.selectedIndex).value == valueHide){
		document.querySelector("#"+blockHide).style.display = 'none';
		document.querySelector("#"+inputClean).value = '';
		return;
	}else{
		document.querySelector("#"+blockHide).style.display = '';
	}
};

Dmp.toggleElement = function(element){
	var el = document.querySelector("#"+element);
	if(el.style.display == 'none'){
		el.style.display = '';
	}else{
		el.style.display = 'none';
	}
};