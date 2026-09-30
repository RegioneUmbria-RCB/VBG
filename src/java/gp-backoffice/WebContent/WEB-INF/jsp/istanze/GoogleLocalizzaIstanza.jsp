<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
	<link href="http://code.google.com/apis/maps/documentation/javascript/examples/standard.css" rel="stylesheet" type="text/css" />
	<title>
		<c:if test="${istanzeCommand.entity.id.codice == null}">
			<fmt:message key="istanzeCommand.label.nuovo_istanzeCommand.title" />
		</c:if> 
		<c:if test="${istanzeCommand.entity.id.codice != null}">
			<fmt:message key="istanzeCommand.label.dettaglio_istanzeCommand.title" />
		</c:if>
	</title>	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/prototype.js"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/scriptaculous/scriptaculous.js"></script>
	<script type="text/javascript"> 
	  var geocoder;
	  var map;
	  function initialize() {
	    geocoder = new google.maps.Geocoder();
	    // var latlng = new google.maps.LatLng(0, 0);
	    var myOptions = {
	      zoom: 16,
	      // center: latlng,
	      mapTypeId: google.maps.MapTypeId.HYBRID //ROADMAP
	    }
	    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	    
	    //All'evento click aggiunge un maker tramite la funzione placeMarker(location)
	    //google.maps.event.addListener(map, 'click', function(event) {
	    //	placeMarker(event.latLng);
		//});
	
		var address = "${istanzestradario.stradario.comune.comune} ${istanzestradario.stradario.descrizioneCompleta} ${istanzestradario.civico}";
	    if (("${lat}" != "") && ("${lng}"!=""))
	    {
	    	//Muove la mappa alle coordinate specificate
	    	var location = new google.maps.LatLng("${lat}", "${lng}");
	    	placeMarker(location,address); //Muove la mappa all'indirizzo specificato per default
	    }
	    else
	    {
	    	//Muove la mappa all'indirizzo specificato
	    	codeAddress(address);
	    }

	  }
	 
		function codeAddress(address) 
		{			
			geocoder.geocode({ 'address': address},
				function(results, status) 
				{
					if (status == google.maps.GeocoderStatus.OK)
					{		
						//map.setCenter(results[0].geometry.location);
						placeMarker(results[0].geometry.location,address);
					} 
					else 
					{
						alert("[Geocode] L'indirizzo ${istanzestradario.stradario.comune.comune} ${istanzestradario.stradario.descrizioneCompleta} ${istanzestradario.civico} non è stato trovato: " + status);
					}
				});
		}
	  
		function placeMarker(location, address)
		{			
			var casina = new google.maps.MarkerImage('${pageContext.request.contextPath}/images/casa.jpg',
				// This marker is 20 pixels wide by 20 pixels tall.
				new google.maps.Size(20, 20),
				// The origin for this image is 0,0.
				new google.maps.Point(0,0),
				// The anchor for this image is the base of the flagpole at 0,20.
				new google.maps.Point(0, 20),
				//To scale the image, whether sprited or not, set the value of scaledSize to the size of the whole image and set size, origin and anchor in scaled values
				new google.maps.Size(20, 20));
							
			var marketDraggable = false;
			if ("${istanzeCommand.displayMode}" == "2")
				marketDraggable = true;

			//Crea un marker in location
			var marker = new google.maps.Marker(
			{
	    			map: map, 
	    			position: location,
	    			draggable: marketDraggable,
	    			icon: casina
			});
			
			marker.setTitle(address);
	
			//All'evento dragend del marker richiama la funzione savePosition
			google.maps.event.addListener(marker, 'dragend', function() {
	    			savePosition(marker);
				});
				
			//All'evento click del marker visualizza le informazioni dell'indirizzo su una finestra
			attachMessage(marker,"<center>Riferimenti pratica</center>Numero: <b>${istanzeCommand.entity.numeroistanza}</b><br>Data: ${istanzeCommand.entity.data}<br>Richiedente: ${istanzeCommand.entity.richiedente.nome} ${istanzeCommand.entity.richiedente.nominativo} - ${istanzeCommand.entity.richiedente.codicefiscale} ${istanzeCommand.entity.richiedente.partitaiva}<br>Intervento: ${istanzeCommand.entity.alberoproc.vwAlberoproc.scDescrizione}<br>Modulo: ${istanzeCommand.entity.software.descrizionelunga}");
		 	
			//Setta location come centro della mappa
			map.setCenter(location);
		}
		
		function savePosition(marker)
		{
			salvaIstanzeStradario(marker.position);
		}
	
		function attachMessage(marker, message) {
		  var infowindow = new google.maps.InfoWindow(
		      { content: message
		      });
		  google.maps.event.addListener(marker, 'click', function() {
		    infowindow.open(map,marker);
		  });
		}


		function salvaIstanzeStradario(position){
			
			new Ajax.Request('../istanze/ajaxGoogleSaveIdStradario.htm', {
				  method: 'post',
				  parameters: {idStradario: '${istanzestradario.id.codice}', lat: position.lat(), lng: position.lng()},
				  onSuccess: function(transport){
					  var response = transport.responseText;		
				    },
				  onFailure: function(transport){ 
					var response = transport.responseText;
				    	alert(response); 
				    }						    		 
				  });

		}
		
	</script>
	
</head>
<body  onload="initialize();">

	<div id="map_canvas" style="height:90%;"></div>

</body>
</html>