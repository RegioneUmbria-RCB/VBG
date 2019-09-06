<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="FileDownload.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.FileDownload" %>
<asp:HyperLink runat="server" 
                ID="hlFileDownload" 
                NavigateUrl='<%# DataBinder.Eval(DataSource, "CodiceOggetto", "~/MostraOggetto.ashx?IdComune=" + IdComune + "&CodiceOggetto={0}") %>' 
                Text='<%#DataBinder.Eval(DataSource,"NomeFile") %>' 
                Target="_blank"/>
