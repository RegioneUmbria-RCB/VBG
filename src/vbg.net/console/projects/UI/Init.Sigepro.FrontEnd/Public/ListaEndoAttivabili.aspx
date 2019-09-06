<%@ Page Title="" Language="C#" MasterPageFile="~/AreaRiservataPopupMaster.Master" AutoEventWireup="true" CodeBehind="ListaEndoAttivabili.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.ListaEndoAttivabili" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

	<asp:Repeater runat="server" ID="rptTipiEndo">
		<ItemTemplate>
			<li class="endo">
				<dl>
					<%--<dt>Endoprocedimento eventuale</dt>--%>
					<dd><%# GetLinkEndo( DataBinder.Eval(Container, "DataItem") ) %> </dd>
				</dl>
			</li>
		</ItemTemplate>
	</asp:Repeater>

</asp:Content>
