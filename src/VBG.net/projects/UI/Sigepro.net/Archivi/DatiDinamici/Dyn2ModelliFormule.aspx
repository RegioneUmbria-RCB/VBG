<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" ValidateRequest="false" AutoEventWireup="true" Codebehind="Dyn2ModelliFormule.aspx.cs"
	Inherits="Sigepro.net.Archivi.DatiDinamici.Dyn2ModelliFormule" Title="Formula scheda" %>

<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ Register Src="../../AdminSecurity/AdminLoginControl.ascx" TagName="AdminLoginControl" TagPrefix="uc1" %>
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
      		/*margin-left:250px;*/
      		width: 100%;
      		height: 500px;
      }
      .CodeMirror-scroll
      {
      	height: 500px;
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
        
       
        div.linksUtili > span
        {
        	width: 300px;
        	display: block;
        	margin-left: 265px;
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
			g_codeMIrror = CodeMirror.fromTextArea(document.getElementById('<%= txtScript.ClientID%>'),{
									mode: "text/x-csharp",
									lineNumbers: true,
									theme: 'neat',
									tabMode: 'default',
									tabSize: 4,
									smartIndent: false,
									indentUnit: 4,
									indentWithTabs: true,
									enterMode: 'keep',
									onCursorActivity: function() {
										g_codeMIrror.setLineClass(hlLine, null);
										hlLine = g_codeMIrror.setLineClass(g_codeMIrror.getCursor().line, "activeline");
									},
									extraKeys: {
										"F11": function() {
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
										"Esc": function() {
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

	function mostraEsempi() {
		showpopup("PopupFormuleDatiDinamici.aspx");
	}

	function inserisciAssegnazione() {
		showpopup("PopupInserisciAssegnazione.aspx?token=<%=Token%>&idmodello=<%=IdModello%>&software=<%=Software %>");
	}

	function inserisciMostraCampoDyn() {
		showpopup("PopupVisualizzaCampo.aspx?token=<%=Token%>&idmodello=<%=IdModello%>&software=<%=Software %>");
	}

	function inserisciMostraCampoStatico() {
		showpopup("PopupVisualizzaCampo.aspx?token=<%=Token%>&idmodello=<%=IdModello%>&software=<%=Software %>&campiStatici=true");
	}
	

	function showpopup(url)
	{
		var winWidth = "600";
		var winHeight = "550";
		var w = window.open( url , "" , "centerscreen=yes,width=" + winWidth + ",height=" + winHeight + ",scrollbars=yes,location=no");
	}

	function insertText(value) {
		g_codeMIrror.replaceSelection(value);
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
		<asp:View runat="server" ID="loginView">
			<uc1:AdminLoginControl ID="AdminLoginControl1" runat="server" OnAuthenticationAborted="AdminAuthenticationKo" OnAuthenticationSucceded="AdminAuthenticationOk" />
		</asp:View>
		<asp:View runat="server" ID="detailsView">
			<fieldset>
				
				<init:LabeledDropDownList runat="server" ID="ddlEvento" Descrizione="Evento" Item-AutoPostBack="true" OnValueChanged="ddlEvento_ValueChanged" />

				<div class="linksUtili">
					<label>Utilità</label>
					<span>
						<a href="javascript:inserisciAssegnazione()">Inserisci formula di assegnazione</a><br />
						<a href="javascript:inserisciMostraCampoDyn()">Visualizza/Nascondi campo diamico</a><br />
						<a href="javascript:inserisciMostraCampoStatico()">Visualizza/Nascondi campo testuale</a><br />
						<a href="javascript:mostraEsempi();" >Esempi di codice</a>
					</span>					
				</div>

				<div>
					
					<asp:TextBox runat="server" ID="txtScript" Columns="100" Rows="25" TextMode="MultiLine" CssClass="CampoFormula" />
					<i>Premere F11 per passare alla modalità a schermo intero (solo Chrome e Opera)</i>
				</div>

				
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
					<asp:Button runat="server" ID="cmdCompila" Text="Verifica compilazione" OnClick="cmdCompila_Click" />
					<asp:Button runat="server" ID="cmdVisualizzaClasse" Text="Visualizza codice generato" OnClick="cmdVisualizzaClasse_Click"/>
					<%--<asp:Button runat="server" ID="cmdCompilaFrontoffice" Text="Verifica compilazione FO" OnClick="cmdCompilaFrontoffice_Click" />--%>
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>

<%--			<script type="text/javascript">
				InizializzaTextArea('<%=txtScript.ClientID %>');
			</script>--%>

		</asp:View>
	</asp:MultiView>
</asp:Content>
