<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GestioneOneri_GrigliaOneri.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneOneri_GrigliaOneri" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" %>

<thead>
	<tr>
		<th><%=EtichettaColonnaCausale%></th>
		<th>Note</th>
		<th>Non pagato</th>
		<th>Tipo pagamento</th>
		<th>Data</th>
		<th>Riferimenti Pagamento</th>
		<th>Importo</th>
	</tr>
</thead>
<asp:Repeater runat="server" ID="rptOneri" OnItemDataBound="rptOneri_ItemDataBound">
	<ItemTemplate>
		<tr>
			<td>
				<asp:HiddenField runat="server" ID="hidIdOnere" Value='<%# Eval("Causale.Codice")%>' />
				<asp:HiddenField runat="server" ID="hidCodiceEndoOIntervento" Value='<%# Eval("EndoOInterventoOrigine.Codice")%>' />
				<asp:Literal runat="server" ID="lblNomeOnere" Text='<%# Eval("Causale")%>' />
				<div class="descrizioneIntervento">[<%# Eval("EndoOInterventoOrigine")%>]</div>
			</td>
			<td class="helpNoteOnereRow">
				<a href="#" class="helpNoteOnere">
					<img src="../../Images/help.png" alt="l'onere contiene note" />
				</a>
				<span class="noteOnere"><%# Eval("Note")%></span>
			</td>
			<td style="text-align:center"><asp:CheckBox runat="server" ID="chkNonPagato" CssClass="chkNonPagato" Checked='<%# DataBinder.Eval(Container.DataItem,"EstremiPagamento") == null %>' data-importo='<%# Eval("Importo","{0:N2}")%>' /></td>
			<td><asp:DropDownList runat="server" ID="ddlTipoPagamento" CssClass="estremiPagamento ddlTipoPagamento" DataTextField="Descrizione" DataValueField="Codice"  /></td>
			<td><cc1:DateTextBox runat="server" ID="txtDataPagamento" CssClass="estremiPagamento txtDataPagamento" Columns="10" Text='<%# Eval("EstremiPagamento.DataPagamento") %>' /></td>
			<td><asp:TextBox runat="server" ID="txtNumeroOperazione" CssClass="estremiPagamento txtNumeroOperazione"  Text='<%# Eval("EstremiPagamento.Riferimento") %>'/></td>
			<td style="text-align:right">
				<%--<asp:Literal runat="server" ID="ltrImportoOnere" Text='<%# Eval("Importo", "€ {0:N2}")%>' Visible='<%# Convert.ToSingle(DataBinder.Eval(Container.DataItem,"Importo")) > 0.0f %>'/>--%>

				<cc1:FloatTextBox runat="server" ID="txtImporto" CssClass="importoPagato estremiPagamento" ValoreFloat='<%# Convert.ToSingle(Eval("ImportoPagato"))%>' Columns="8" ReadOnly='<%# Convert.ToSingle(DataBinder.Eval(Container.DataItem,"Importo")) > 0.0f %>' />
			</td>
		</tr>
	</ItemTemplate>
</asp:Repeater>