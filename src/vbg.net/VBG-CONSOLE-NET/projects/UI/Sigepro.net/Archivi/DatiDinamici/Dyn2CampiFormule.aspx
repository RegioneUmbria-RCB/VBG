<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master"  ValidateRequest="false" AutoEventWireup="True" Inherits="Archivi_DatiDinamici_Dyn2CampiFormule" Title="Formule campi"
	Codebehind="Dyn2CampiFormule.aspx.cs" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>




<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

	<script type="text/javascript" src="<%=ResolveClientUrl("~/js/CodeMirror/lib/codemirror.js") %>"></script>
	<link rel="stylesheet" href="<%=ResolveClientUrl("~/js/CodeMirror/lib/codemirror.css") %>" />
	<script type="text/javascript" src="<%=ResolveClientUrl("~/js/CodeMirror/mode/clike/clike.js") %>"></script>
	<link rel="stylesheet" href="<%=ResolveClientUrl("~/js/CodeMirror/theme/neat.css") %>" />


	<style type="text/css">
		
      .CodeMirror 
      {
      		border: 1px solid black;
      		margin-left:250px;
      		width: 800px;
      }
      .CodeMirror-fullscreen {
            display: block;
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 9999;
            margin: 0;
            padding: 0;
            border: 0px solid #BBBBBB;
            opacity: 1;
            background-color:#fff;
        }
            /*.activeline 
      {
      	background: #f0fcff !important;
      	z-index: -2;
      }*/
    </style>

	<script type="text/javascript">
		var g_codeMIrror = null;

		$(function () {
			g_codeMIrror = CodeMirror.fromTextArea(document.getElementById('<%= txtScript.ClientID%>'), {
				mode: "text/x-csharp",
				lineNumbers: true,
				theme: 'neat',
				tabMode: 'default',
				tabSize: 4,
				smartIndent: false,
				indentUnit: 4,
				indentWithTabs: true,
				enterMode: 'keep',
				onCursorActivity: function () {
					g_codeMIrror.setLineClass(hlLine, null);
					hlLine = g_codeMIrror.setLineClass(g_codeMIrror.getCursor().line, "activeline");
				},
				extraKeys: {
					"F11": function () {
						var scroller = g_codeMIrror.getScrollerElement();
						if (scroller.className.search(/\bCodeMirror-fullscreen\b/) === -1) {
							scroller.className += " CodeMirror-fullscreen";
							scroller.style.height = "100%";
							scroller.style.width = "100%";
							g_codeMIrror.refresh();
						} else {
							scroller.className = scroller.className.replace(" CodeMirror-fullscreen", "");
							scroller.style.height = '';
							scroller.style.width = '';
							g_codeMIrror.refresh();
						}
					},
					"Esc": function () {
						var scroller = g_codeMIrror.getScrollerElement();
						if (scroller.className.search(/\bCodeMirror-fullscreen\b/) !== -1) {
							scroller.className = scroller.className.replace(" CodeMirror-fullscreen", "");
							scroller.style.height = '';
							scroller.style.width = '';
							g_codeMIrror.refresh();
						}
					}
				}
			});

			var hlLine = g_codeMIrror.setLineClass(0, "activeline");
		});
		
		function showpopup(elementId)
		{
			var winWidth = "450px";
			var winHeight = "300px";
			var feats = "dialogWidth=" + winWidth + ";dialogHeight=" + winHeight + ";edge:raised;";
			var rVal = window.showModalDialog( "PopupFormuleDatiDinamici.aspx" , "" , feats);
		
			if (rVal)
			{
				g_codeMIrror.replaceSelection(rVal);
			}
		
			return false;
		}
	
		function copyToClipboard(sender, ctrlId)
		{
			var el = document.getElementById( ctrlId );
			var varName = el.value.replace(/ /g, "_");
			varName = varName.replace( /\'/g,"_" );
			varName = varName.replace( /`/g,"_" );
				
			var data = "var " + varName + " = ModelloCorrente.TrovaCampo(\"" + el.value + "\");";

			//var data = "\"" + el.value + "\"";
			g_codeMIrror.replaceSelection(data);
			g_codeMIrror.focus();
		}


	</script>

	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="1">
		<asp:View runat="server" ID="authenticate">
		</asp:View>
		<asp:View runat="server" ID="edit">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label2" AssociatedControlID="lblCampoCorrente">Campo corrente</asp:Label>
					<asp:TextBox runat="server" ID="lblCampoCorrente" ReadOnly="true" />
					<a href="javascript:copyToClipboard(this,'<% = lblCampoCorrente.ClientID %>')">Inserisci formula assegnazione</a><br />
					<i>La formula di assegnazione verrà inserita nella formula alla posizione del cursore</i>
				</div>
			
				<init:LabeledDropDownList runat="server" ID="ddlEvento" Item-AutoPostBack="true" Descrizione="Evento" OnValueChanged="ddlEvento_ValueChanged" />
				<div>
					<asp:Label runat="server" ID="label1" AssociatedControlID="txtScript">Formula campo</asp:Label>
					<i>Premere F11 per passare alla modalità a schermo intero</i>
					<asp:TextBox runat="server" ID="txtScript"  Columns="100" Rows="25" TextMode="MultiLine" CssClass="CampoFormula"></asp:TextBox>
				</div>

				<div class="Bottoni">
					<init:SigeproButton ID="cmdSalva" runat="server" Text="Salva" IdRisorsa="SALVA"  OnClick="cmdSalva_Click" />
					<init:SigeproButton ID="cmdCompila" runat="server" Text="Compila formule" IdRisorsa="COMPILAFORMULE"  OnClick="cmdCompila_Click" />
					<asp:Button runat="server" ID="cmdVisualizzaClasse" Text="Visualizza codice generato" OnClick="cmdVisualizzaClasse_Click"/>
					<input type="submit" onclick="return showpopup('<%= txtScript.ClientID%>');" value="Esempi di codice" />
					<init:SigeproButton ID="cmdChiudi" runat="server" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>

		</asp:View>
	</asp:MultiView>
</asp:Content>

