// Init the taggit module
$(function () {
	$('.Tags').tagit({
		allowSpaces: true,
		removeConfirmation: true,
		
		onTagRemoved: function(evt, tag) {
			$('.tab-content .active .deleteKw').attr('value', $('.Tags').tagit('tagLabel', tag));
			$('.tab-content .active .deleteKw').parent().submit();
    	}
	}).tagit('option', 'onTagAdded', function(evt, tag) {
		// Add this callbackafter we initialize the widget,
		// so that onTagAdded doesn't get called on page load.
			// Submit the form
			$('.tab-content .active .Tags').parent().submit();
   	});
});


// Init the form module
$(function() {  
	$('.Tags').parent().ajaxForm(); 
	$('.deleteKw').parent().ajaxForm();	
});  

// On/Off button from http://devgrow.com/iphone-style-switches/
$(document).ready( function(){
	$(".cb-enable").click(function(){
		var parent = $(this).parents('.switch');
		$('.cb-disable',parent).removeClass('selected');
		$(this).addClass('selected');
		$('.checkbox',parent).attr('checked', true);
		$("#"+$(this).siblings('input').attr("name")).slideDown("slow");
	});
	$(".cb-disable").click(function(){
		var parent = $(this).parents('.switch');
		$('.cb-enable',parent).removeClass('selected');
		$(this).addClass('selected');
		$('.checkbox',parent).attr('checked', false);
		$("#"+$(this).siblings('input').attr("name")).slideUp("slow");
	});
});
