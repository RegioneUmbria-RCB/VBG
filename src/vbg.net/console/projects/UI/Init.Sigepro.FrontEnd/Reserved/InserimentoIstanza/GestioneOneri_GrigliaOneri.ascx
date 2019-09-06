<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GestioneOneri_GrigliaOneri.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneOneri_GrigliaOneri" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" %>

<thead>
	<tr>
		<th><%=EtichettaColonnaCausale%></th>
		<th style="text-align: center">Note</th>
		<th style="text-align: center">Pagamento</th>
		<th style="text-align: center">Tipo pagamento</th>
		<th style="text-align: center">Data</th>
		<th style="text-align: center">Riferimenti Pagamento</th>
		<th style="text-align: center">Importo</th>
	</tr>
</thead>
<asp:Repeater runat="server" ID="rptOneri" OnItemDataBound="rptOneri_ItemDataBound">
	<ItemTemplate>
		<tr>
			<td>
				<asp:HiddenField runat="server" ID="hidIdOnere" Value='<%# Eval("CodiceCausale")%>' />
				<asp:HiddenField runat="server" ID="hidCodiceEndoOIntervento" Value='<%# Eval("CodiceEndoOInterventoOrigine")%>' />
				<span class="descrizioneCausale"><asp:Literal runat="server" Text='<%# Eval("Causale")%>' id="lblNomeOnere" /></span>
				<div class="descrizioneIntervento">[<%# Eval("EndoOInterventoOrigine")%>]</div>
			</td>
			<td class="helpNoteOnereRow">
				<a href="#" class="helpNoteOnere">
                    <i class="glyphicon glyphicon-question-sign"></i>
				</a>
				<span class="noteOnere" style="display:none"><%# Eval("Note")%></span>
			</td>
			<td style="text-align:center">
                <asp:DropDownList runat="server" id="ddlPagamento" class="ddlPagamento form-control" data-pagamento-completato='<%#Eval("PagamentoCompletato")%>'>
                    <asp:ListItem Value="" Text="Selezionare..." />
                    <asp:ListItem Value="2" Text="Effettuato" />
                    <asp:ListItem Value="0" Text="Non dovuto" />
                </asp:DropDownList>
			</td>
			<td><asp:DropDownList runat="server" ID="ddlTipoPagamento" CssClass="estremiPagamento ddlTipoPagamento form-control" style="max-width: 400px;" DataTextField="Descrizione" DataValueField="Codice"  /></td>
			<td><cc1:DateTextBox runat="server" ID="txtDataPagamento" CssClass="estremiPagamento txtDataPagamento form-control" Columns="10" Text='<%# Eval("DataPagamento") %>' /></td>
			<td><asp:TextBox runat="server" ID="txtNumeroOperazione" CssClass="estremiPagamento txtNumeroOperazione form-control"  Text='<%# Eval("RiferimentoPagamento") %>'/></td>
			<td style="text-align:right">
				<cc1:FloatTextBox runat="server" ID="txtImporto" CssClass="importoPagato estremiPagamento form-control" ValoreFloat='<%# Convert.ToSingle(Eval("ImportoPagato"))%>' Columns="8" ReadOnly='<%# Convert.ToSingle(DataBinder.Eval(Container.DataItem,"Importo")) > 0.0f %>' />
			</td>
		</tr>
	</ItemTemplate>
</asp:Repeater>