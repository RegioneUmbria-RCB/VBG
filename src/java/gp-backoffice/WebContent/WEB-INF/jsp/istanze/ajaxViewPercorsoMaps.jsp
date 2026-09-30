
<%@ include file="../includes/taglibs.jsp"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<style>
        #map_percorso {
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
        }

        #datiPercorso {
            display:none;
        }

    </style>
 	<div id="map_percorso"></div>
 	<textarea id="datiPercorso" cols="80" rows="3">
        ${ json }
    </textarea>
 	
 	<script>
 	jQuery(function(){
        var datiPercorso = JSON.parse(document.getElementById("datiPercorso").value);

        var renderer = new google.maps.DirectionsRenderer();
        
        var map = new google.maps.Map(document.getElementById('map_percorso'));

        renderer.setMap(map);
        renderer.setDirections(datiPercorso.percorso);
	});
    </script>
 	

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${ apiKey }&libraries=places"></script>
