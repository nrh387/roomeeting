/** Global item repository for RooMeeting js lib */
if(!RooMeeting)
{
	var RooMeeting = {};
}

jQuery(document).ready(function () {
	jQuery('.datepicker-input').datepicker({
		autoclose : true,
		format:"dd/mm/yyyy",
		weekStart:"1",
		language:'fr'
	});
	jQuery(".tooltiped").tooltip();
});