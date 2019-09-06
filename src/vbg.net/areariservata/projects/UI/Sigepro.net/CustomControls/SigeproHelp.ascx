<%@ Control Language="C#" AutoEventWireup="true" Inherits="CustomControls_SigeproHelp" Codebehind="SigeproHelp.ascx.cs" %>
    <div id="HelpTop"></div>
    <div id="HelpContent">
		<hr />
        <ul>
            <li><a href="javascript: void 0;" onclick="window.open('<%=GetHelpPath() %>',2,'width=620,height=370,top=30,left=30,menubar=no,scrollbars=no,resizable=yes,status=yes');" >Visualizza l'help per questa pagina.</a></li>
        </ul>
    </div>
    <div id="HelpBottom"></div>
