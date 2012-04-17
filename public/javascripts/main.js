// Init the taggit module
$(function(){$('#Tags').tagit({allowSpaces: true, removeConfirmation: true});});

// On/Off button from http://devgrow.com/iphone-style-switches/
$(document).ready( function(){
	$(".cb-enable").click(function(){
		var parent = $(this).parents('.switch');
		$('.cb-disable',parent).removeClass('selected');
		$(this).addClass('selected');
		$('.checkbox',parent).attr('checked', true);
		$("#mailset").slideDown("slow");
	});
	$(".cb-disable").click(function(){
		var parent = $(this).parents('.switch');
		$('.cb-enable',parent).removeClass('selected');
		$(this).addClass('selected');
		$('.checkbox',parent).attr('checked', false);
		$("#mailset").slideUp();
	});
});
