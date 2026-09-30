<html>
<head>
	<script src="js/jquery-1.10.2.js" type="text/javascript"></script>
	<script src="js/jquery-ui-1.12.1.min.js" type="text/javascript"></script>
	<script src="js/jquery.format.js" type="text/javascript"></script>
	<script src="js/jquery.datetimepicker.js" type="text/javascript"></script>
	<script src="js/funzioni.js" type="text/javascript"></script>
	<link href="css/jquery-ui-1.12.1.min.css" rel="stylesheet"></link>
	<link href="css/jquery.datetimepicker.css" rel="stylesheet"></link>
	<link href="css/custom.css" rel="stylesheet"></link>
</head>
<body>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Dati</a></li>
		</ul>
			<div id="tabs-1">
				<div class="servizio_title">PROCESSA ATTIVAZIONE</div>
				<div class="servizio">					
					<div class="servizio_form">
						<div class="riga">
							<div>Indirizzo security</div>
							<div>${security_url }</div>
						</div>	
						<div class="riga">
							<div>idOperazione:</div>
							<div><input type="text" id="idOperazione"value="<%= System.currentTimeMillis() %>" /></div>
						</div>	
						<div class="riga">
							<div>aliasOrigine</div>
							<div><input type="text" id="aliasOrigine" value="BOCCI" /></div>
						</div>	
						<div class="riga">
							<div>softwareOrigine:</div>
							<div><input type="text" id="softwareOrigine" value="SS" /></div>
						</div>	
						<div class="riga">
							<div>aliasDestinazione</div>
							<div><input type="text" id="aliasDestinazione" value="CMIS" /></div>
						</div>	
						<div class="riga">
							<div>softwareDestinazione</div>
							<div><input type="text" id="softwareDestinazione" value="SS" /></div>
						</div>	
						<div class="riga">
							<div>username:</div>
							<div><input type="text" id="username" value="" /></div>
						</div>	
						<div class="riga">
							<div>password:</div>
							<div><input type="password" id="password" value="" /></div>
						</div>		
						<div class="riga">
							<div>escludi disabilitati:</div>
							<div><input type="checkbox" id="escludiDisabilitati" value="" /></div>
						</div>							
						<div class="riga">
							<div><button onclick="return sincronizza();">sincronizza</button>&nbsp;<button onclick="stopMonitor();">stop</button></div>
						</div>						
					</div>
					<div id="output" class="output"></div>
							
				</div>
				<div style="clear: both;">&nbsp;</div>	
		</div>
	</div>

		<script type="text/javascript">
		
		
			$(function() {
				$("#tabs").tabs();
			});
			
			function sincronizza() {

				var idOperazione = $('#idOperazione').val();
				var aliasOrigine = $('#aliasOrigine').val();
				var softwareOrigine = $('#softwareOrigine').val();
				var aliasDestinazione =  $('#aliasDestinazione').val();
				var softwareDestinazione =  $('#softwareDestinazione').val();
				
				var username =  $('#username').val();
				var password =  $('#password').val();
				
				var escludiDisabilitati =  $('escludiDisabilitati').is(':checked');
				startMonitor(idOperazione);

				$.ajax({
					url : "sincronizza.htm",
					method: "POST",
					cache: false,
					data: "idOperazione="+idOperazione+"&aliasOrigine="+aliasOrigine+"&softwareOrigine="+softwareOrigine+"&aliasDestinazione="+aliasDestinazione+"&softwareDestinazione="+softwareDestinazione+"&username="+username+"&password="+password+"&escludiDisabilitati="+escludiDisabilitati,
					success : function(result) {
						stopMonitor();
					}
				});

			}

			var ferma = false;

			function startMonitor(idOperazione) {
				if (window.ferma == false) {
					
					var date = new Date();
					$.ajax({
						url : "status.htm?idOperazione=" + idOperazione,
						success : function(result) {
							$('#output').prepend(
									"<p>" + date.formatTime() + "<br /><strong>"+ result + "</strong></p>");
							setTimeout("startMonitor('" + idOperazione + "')",
									2000);
						}
					});

				}
			}
			function stopMonitor() {
				window.ferma = true;
				var date = new Date();
				$('#output').prepend(
						'<p>'+ date.formatTime() + '<br /><strong>operazione terminata</strong></p>');
			}
			
			
			
		</script>
</body>
</html>