<%@ Page Title="Riepilogo della firma digitale del documento" Language="C#" MasterPageFile="~/AreaRiservataPopupMaster.Master" AutoEventWireup="true" CodeBehind="MostraOggettoP7M.aspx.cs" Inherits="Init.Sigepro.FrontEnd.MostraOggettoP7M" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

	<asp:Repeater runat="server" ID="rptEsitiVerificaFirma">
		<ItemTemplate>

				<h2>Certificato di firma</h2>

				<ar:LabeledLabel runat="server" id="lblSoggetto" Label="Soggetto" Value='<%# Bind("Soggetto") %>' />
				<ar:LabeledLabel runat="server" id="lblEmittente" Label="Emittente" Value='<%# Bind("Emittente") %>' />
				<ar:LabeledLabel runat="server" id="lblValidoDal" Label="Valido Dal" Value='<%# Bind("ValidoDal") %>'/>
				<ar:LabeledLabel runat="server" id="lblValidoAl" Label="Valido Al" Value='<%# Bind("ValidoAl") %>'/>
				<ar:LabeledLabel runat="server" id="lblSeriale" Label="Seriale" Value='<%# Bind("Seriale") %>'/>
				<ar:LabeledLabel runat="server" id="LabeledLabel1" Label="Firma valida" Value='<%# Eval("EsitoVerificaFirma").ToString() == "True" ? "Si" : "No" %>'/>
				<ar:LabeledLabel runat="server" id="LabeledLabel2" Label="Revocato" Value='<%# Eval("EsitoVerificaRevoca").ToString() == "True" ? "No" : "Si" %>'/>

				<h2>Verifica revoca certificati</h2>
				<asp:Repeater runat="server" ID="rptVerificaCertificati" DataSource='<%# Bind("DettaglioVerificaRevoca") %>'>
					<HeaderTemplate>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Soggetto</th>
									<th>Emittente</th>
									<th>Validità</th>
									<th>Stato</th>
									<th>Revoca</th>
								</tr>
							</thead>
							<tbody>
					</HeaderTemplate>
					<ItemTemplate>
						<tr>
							<td><asp:Literal ID="Literal0" runat="server" Text='<%# Bind("Soggetto") %>' /></td>
							<td><asp:Literal ID="Literal1" runat="server" Text='<%# Bind("Emittente") %>' /></td>
							<td><asp:Literal ID="Literal2" runat="server" Text='<%# Eval("VerificaValiditaAlTempoDellaFirma").ToString() == "True" ? "Valido" : "Non valido" %>' /></td>
							<td><asp:Literal ID="Literal3" runat="server" Text='<%# Eval("Revocato").ToString() == "True" ? "Revocato" : "Valido" %>' /></td>
							<td><asp:Literal ID="Literal4" runat="server" Text='<%# Eval("DataDiRevoca") %>' /></td>
						</tr>
					</ItemTemplate>
					<FooterTemplate>
							</tbody>
						</table>
					</FooterTemplate>
				</asp:Repeater>

		</ItemTemplate>
	</asp:Repeater>


	<div class="bottoni">
		<asp:Button runat="server" ID="lnkDownloadFileNoFirma"  Text="Scarica file senza firma digitale" OnClick="lnkDownloadFileNoFirma_Click" />
		<asp:Button runat="server" ID="Button1"  Text="Scarica file con firma digitale" OnClick="lnkDownloadFile_Click" />
		<asp:Button runat="server" ID="cmdClose"  Text="Chiudi" OnClientClick="self.close();" />
	</div>


</asp:Content>
