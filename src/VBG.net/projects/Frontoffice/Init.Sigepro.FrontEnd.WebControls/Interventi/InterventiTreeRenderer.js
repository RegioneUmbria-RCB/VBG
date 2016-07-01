/*var g_accordionRegistered = false;

$(function() {
	var el = $("#dettagliIntervento");

	if (el.length == 0)
		$('#contenuti').append("<div id='dettagliIntervento'></div>");

	$(".noteIntervento").click(function(e) {

		var oldHtml = $(this).html();
		var clickElement = $(this);

		clickElement.html("&nbsp;Caricamento in corso...");

		e.preventDefault();

		var el = $("#dettagliIntervento");

		el.html("");
		el.css('display','block');
		el.load(this.href, null, function(responseText, textStatus, XMLHttpRequest) {
		
			clickElement.html(oldHtml);

			

			$(this).dialog({ height: 500,
				width: 600,
				title: "Dettagli dell\'intervento",
				modal: true,
				open: function(){
					$('#accordion').accordion({ header: "h3", autoHeight: false });
		
					$("#dettagliIntervento .treeViewDettagli ul").treeview({
						animated: "fast"
					});
					
					$('#accordion table TR:even').addClass('AlternatingItemStyle');
					$('#accordion table TR:odd').addClass('ItemStyle');
				
				}
			});
		});
	});
});
*/