export const pageJs = (function () {
	let divComandi = null,
		divSalvataggioNote = null,
		txtNote = null;

	return {

		init: function () {
			divComandi = $('#divComandi');
			divSalvataggioNote = $('#divSalvataggioNote');
			txtNote = $('#txtNote');

			divSalvataggioNote.css('display', 'none');

			txtNote.off().on('change', function () {
				divSalvataggioNote.fadeIn();
				divComandi.fadeOut();
			});

			txtNote.one('keydown', function () {
				divSalvataggioNote.fadeIn();
				divComandi.fadeOut();
			});
		},

		reset: function () {
			divSalvataggioNote.fadeOut();
			divComandi.fadeIn();
        }
    }
	
})();