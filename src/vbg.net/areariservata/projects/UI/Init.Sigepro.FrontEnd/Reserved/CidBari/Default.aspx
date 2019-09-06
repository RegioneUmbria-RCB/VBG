<%@ Page Title="Generazione CID/PIN" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.CidBari.Default" %> <%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %> <asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
	<style>
		.datiCid > div
		{
			margin-bottom: 10px;
		}
		
		.datiCid > div > label
		{
			float:none !important;
			font-weight: bold;
		}
		
		.datiCid > div > span
		{
			display: block;
		}
	</style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

	<div class="inputForm">
		<asp:MultiView runat="server" ID="MultiView">
			<asp:View runat="server">
				<fieldset>
					<div>
						<label>Cid contribuente</label>
						<asp:TextBox runat="server" ID="txtCid"></asp:TextBox>
					</div>
					<div>
						<label>Codice fiscale contribuente</label>
						<asp:TextBox runat="server" ID="txtCodiceFiscale"></asp:TextBox>
					</div>

					<div class="bottoni">
						<asp:Button runat="server" ID="cmdGenera" Text="Genera" OnClick="cmdGenera_Click" />
					</div>
				</fieldset>

			</asp:View>

			<asp:View runat="server">
				<script type="text/javascript">
				    function onChiudi() {
				        $('#<%=hidXml.ClientID %>').val('');

					    return true;
					}

					function printDoc() {

					    var oldDisplay = $('#menuNavigazione').css('display');
					    var oldPadding = $('#contenuti').css('padding-left');


					    $('#menuNavigazione').css('display', 'none');
					    $('#datiUtenteV2').css('display', 'none');
					    $('#contenuti').css('padding-left', '0px');
					    $('.bottoni').hide();

					    window.print();

					    $('#menuNavigazione').css('display', oldDisplay);
					    $('#datiUtenteV2').css('display', oldDisplay);
					    $('#contenuti').css('padding-left', oldPadding);
					    $('.bottoni').show();

					    return false;
					}
				</script>
				<fieldset class="datiCid">

					<asp:HiddenField runat="server" ID="hidXml" />

					<div>
						<label>Nominativo</label>
						<asp:Label runat="server" id="txtOutNominativo" ReadOnly="true" Columns="60" />
					</div>

					<div>
						<label>Nato il</label>
						<asp:Label runat="server" id="txtOutDataNascita" ReadOnly="true" Columns="12"  />
					</div>

					<div>
						<label>Codice fiscale</label>
						<asp:Label runat="server" id="txtOutCodiceFiscale" ReadOnly="true" />
					</div>

                    <% if (Software == "T1") {%>
					<div>
						<label>Cid</label>
						<asp:Label runat="server" id="txtOutCin" ReadOnly="true"  Columns="50"/>
					</div>
                    <%} %>


					<div>
						<label>Pin</label>
						<asp:Label runat="server" id="txtOutPin" ReadOnly="true"  Columns="50"/>
					</div>

					<div class="bottoni">
						<asp:Button runat="server" ID="cmdPrint" Text="Stampa" OnClientClick="printDoc();" />
						
						<asp:Button runat="server" ID="cmdAccedi" Text="Accedi" />
						
						<asp:Button runat="server" ID="cmdChiudi" Text="Chiudi" OnClick="cmdChiudi_Click" OnClientClick="return onChiudi();" />
					</div>
				</fieldset>

			</asp:View>

		</asp:MultiView>
	</div>
	<script type="text/javascript">
	    function createInput(name, value) {
	        var input = $('<input />', {
	            type: 'hidden',
	            name: name,
	            value: value
	        });

	        return input;
	    }

	    function redirectToLogin() {
	        var outCin = $('#<%=txtOutCin.ClientID%>').text(),
				outPin = $('#<%=txtOutPin.ClientID%>').text(),
				returnTo = 'http://tributionline.comune.bari.it/AreaRiservata/Reserved/default.aspx?idComune=A662&software=TO',
				x = 31,
				y = 13;

            console.log(outCin + " - " + outPin);

            var form = $('<form />', {
                action: 'http://tributionline.comune.bari.it/ibcauthenticationgateway/loginbariute',
                method: 'post'
            });

            createInput('return_to', returnTo).appendTo(form);
            createInput('idcomunealias', 'A662').appendTo(form);
            createInput('software', 'TO').appendTo(form);
            createInput('contesto', 'UTE').appendTo(form);
            createInput('cid', outCin).appendTo(form);
            createInput('pin', outPin).appendTo(form);
            createInput('x', x).appendTo(form);
            createInput('y', y).appendTo(form);

            form.appendTo($('body'));
            form[0].submit();
        }

        $(function () {
            $('#<%=cmdAccedi.ClientID%>').on('click', function (e) {
		        e.preventDefault();
		        redirectToLogin();
		    });
		});
	</script>
</asp:Content>
