<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="PopupFormuleDatiDinamici.aspx.cs" Inherits="Sigepro.net.Archivi.DatiDinamici.PopupFormuleDatiDinamici" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
	<link href="~/Stili/popupFormuleDatiDinamici.css" rel="Stylesheet" type="text/css" />
    <title>Gestione formule dati dinamici</title>
        <script type="text/javascript">
		/***************************************************************************************************************************************************
			CLASSE SNIPPETPARAMETER
		*/
		SnippetParameter = function( parameterName , parameterDescription , isFieldName )
		{
			this._parameterName = parameterName;
			this._parameterDescription = parameterDescription;
			this._isFieldName = isFieldName;
		}
		
		SnippetParameter.prototype = {
			GetParameterName : function()
			{
				return unescape(this._parameterName);
			},
			GetParameterDescription : function()
			{
				return unescape(this._parameterDescription);
			},
			IsFieldName : function()
			{
				return this._isFieldName;
			}
			
		}
		/***************************************************************************************************************************************************
			CLASSE SNIPPET
		*/    
		Snippet = function( description )
		{
			this._description = description;
			this._parameters = new Array();
			this._codeSnippet = "";
		}
		
		Snippet.prototype = {
			AddParameter : function( parameter )
			{
				this._parameters.push( parameter );
			},
			GetParameters : function()
			{
				return this._parameters;
			},
			SetCodeSnippet : function( code )			
			{
				this._codeSnippet = code;
			},
			GetCodeSnippet : function()
			{
				return unescape(this._codeSnippet);
			},
			GetDescription : function()
			{
				return unescape(this._description);
			}
		}
		
		/***************************************************************************************************************************************************
			CLASSE SNIPPETGROUP
		*/
		SnippetGroup = function(groupName)
		{
			this._snippets = new Array();
			this._name = groupName;
		}
		
		SnippetGroup.prototype = {
			AddSnippet : function( snippet )
			{
				this._snippets.push( snippet );
			},
			GetSnippet : function( snippetIdx )
			{
				return this._snippets[snippetIdx];
			},
			GetSnippetCount : function()
			{
				return this._snippets.length;
			},
			GetGroupName : function()
			{
				return this._name;
			}
		}
    
    	/***************************************************************************************************************************************************
			CLASSE SNIPPETWIZARD
		*/
		SnippetWizard = function( )
		{
			this._step = 0;
			this._selectedSnippetId = -1;
			this._container;
			this._listDiv;
			this._parameterDiv;
			this._fieldNameDiv;
			this._snippetText;
			this.Groups = new Object();
		}

		SnippetWizard.prototype =
		{
			AddSnippetGroup: function(groupName) {
				this.Groups[groupName] = new SnippetGroup(groupName);
			},
			SetContainer: function(container) {
				this._container = container;
			},
			Start: function(snippetId) {
				this._listDiv.style.display = "none";

				this.SetSelectedSnippet(snippetId);
				this._snippetText = unescape(this.GetCurrentSnippet().GetCodeSnippet());//.replace(/\+/g, " ");

				this.ShowParametersEditor();
			},
			ShowParametersEditor: function() {
				if (this._step >= this.GetCurrentSnippet().GetParameters().length) {
					this.WriteSnippet();
					return;
				}

				var parameter = this.GetCurrentSnippet().GetParameters()[this._step];

				this._listDiv.style.display = "none";
				this._parameterDiv.style.display = "none";
				this._fieldNameDiv.style.display = "none";

				if (!parameter.IsFieldName()) {
					this._parameterDiv.inputTextBox.value = "";
					this._parameterDiv.descriptionLabel.innerHTML = parameter.GetParameterDescription();

					this._parameterDiv.style.display = "";
				}
			},
			GetCurrentSnippet: function() {
				if (this._selectedSnippetId.indexOf('$') < 0) return null;

				var selId = this._selectedSnippetId.split('$');
				var groupName = selId[0];
				var snippetIdx = selId[1];

				return this.Groups[groupName].GetSnippet(snippetIdx);
			},
			Continue: function(value) {
				var parName = this.GetCurrentSnippet().GetParameters()[this._step].GetParameterName();
				this._snippetText = this._snippetText.replace(new RegExp("@" + parName + "@", "g"), value);
				//				alert( this._snippetText );

				this._step++;

				this.ShowParametersEditor();
			},
			Abort: function() {
				this.SetSelectedSnippet('');
			},
			WriteSnippet: function() {
				// window.returnValue = this._snippetText;
				window.opener.insertText(this._snippetText);
				self.close();
			},
			SetSelectedSnippet: function(snippetId) {
				this._step = 0;
				this._selectedSnippetId = snippetId;

				if (snippetId.indexOf('$') < 0) {
					this._listDiv.style.display = "";
					this._parameterDiv.style.display = "none";
					this._fieldNameDiv.style.display = "none";

					this._snippetText = "";
				}
			},
			Render: function() {
				if (!this._container) {
					alert("Container non specificato");
					return;
				}
				this._container.innerHTML = "";

				// Creo i div che conterranno gli elementi
				this._listDiv = document.createElement("DIV");
				this._parameterDiv = document.createElement("DIV");
				this._fieldNameDiv = document.createElement("DIV");

				this._container.appendChild(this._listDiv);
				this._container.appendChild(this._parameterDiv);
				this._container.appendChild(this._fieldNameDiv);

				// Inizializzo il div che contiene la lista di snippets

				var divTitolo = document.createElement("DIV");

				divTitolo.appendChild(document.createTextNode("Formule Disponibili"));
				divTitolo.className = "intestazione";

				this._listDiv.appendChild(divTitolo);

				// Rendering del gruppo
				for (var snippetGroupName in this.Groups) {
					this._listDiv.appendChild(this.RenderSnippetGroup(this.Groups[snippetGroupName]));
				}

				var btn = document.createElement("input");
				btn.type = "button";
				btn.value = "Chiudi";
				btn.onclick = function() {
					window.returnValue = "";
					self.close();
				}
				this._listDiv.appendChild(btn);

				// inizializzo il div per l'editing dei parametri
				var lbldescPar = document.createElement("label");
				var txtInput = document.createElement("input");
				var br = document.createElement("br");

				var btnContinua = document.createElement("input");
				var btnAnnulla = document.createElement("input");

				divTitolo = document.createElement("DIV");
				divTitolo.appendChild(document.createTextNode("Valore parametro"));
				divTitolo.className = "intestazione";
				this._parameterDiv.appendChild(divTitolo);

				txtInput.type = "text";
				txtInput.id = "textInput";

				lbldescPar.setAttribute("for", "textInput");
				lbldescPar.innerHTML = "Descrizione"

				btnContinua.type = btnAnnulla.type = "button";
				btnContinua.txtInput = txtInput;
				btnContinua.value = "Continua"
				btnContinua.onclick = function() {
					g_wizard.Continue(this.txtInput.value);
				}

				btnAnnulla.value = "Annulla";
				btnAnnulla.onclick = function() {
					g_wizard.Abort();
				}
				this._parameterDiv.inputTextBox = txtInput;
				this._parameterDiv.descriptionLabel = lbldescPar;

				this._parameterDiv.appendChild(lbldescPar);
				this._parameterDiv.appendChild(br);
				this._parameterDiv.appendChild(txtInput);

				br = document.createElement("br");

				this._parameterDiv.appendChild(br);
				this._parameterDiv.appendChild(btnContinua);
				this._parameterDiv.appendChild(btnAnnulla);

				// inizializzo il div per la lista dei campi del modello


				this.SetSelectedSnippet('');
			},
			RenderSnippetGroup: function(snippetGroup) {
				var titoloGruppo = document.createElement("SPAN");
				var ul = document.createElement("UL");

				var groupName = snippetGroup.GetGroupName();

				titoloGruppo.innerHTML = groupName;
				titoloGruppo.className = "titoloGruppo";

				for (var i = 0; i < snippetGroup.GetSnippetCount(); i++) {
					var snippet = snippetGroup.GetSnippet(i);

					var snippetId = groupName + '$' + String(i);

					var li = document.createElement("LI");
					var href = document.createElement("A");
					var text = document.createTextNode(snippet.GetDescription());

					href.href = "#";
					href.snippetId = snippetId;
					href.onclick = function() {
						g_wizard.Start(this.snippetId);
					};

					href.appendChild(text);
					li.appendChild(href);
					ul.appendChild(li);
				}

				var divGruppo = document.createElement("DIV");
				divGruppo.className = "bloccoGruppo";
				divGruppo.appendChild(titoloGruppo);
				divGruppo.appendChild(ul);

				return divGruppo;
			}

		}
   
   
		var g_wizard = new SnippetWizard();
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div id="container">
		
    </div>
    </form>
</body>
</html>
