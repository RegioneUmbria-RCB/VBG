<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="MercatiD.aspx.cs" Inherits="Sigepro.net.Archivi.Mercati.MercatiD" Title="Dettaglio del mercato" %>

<%@ Register Src="Posteggio.ascx" TagName="Posteggio" TagPrefix="uc1" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="View1">
			<fieldset>
		        <div>
					<asp:Label runat="server" ID="lblMercato" Text="Mercato" AssociatedControlID="rplMercato" />
					<init:RicerchePlusCtrl AutoPostBack="true" ID="rplMercato" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="CodiceMercato" ServicePath="~/Webservices/WsSIGePro/RicerchePlus.asmx" AutoSelect="True" ServiceMethod="GetCompletionList" OnValueChanged="rplMercato_ValueChanged" />
		        </div>
                <div>
					<asp:Label runat="server" ID="lblUso" Text="Giorno" AssociatedControlID="rplMercatiUso"/>
					
					<asp:UpdatePanel ID="UpdatePanel1" runat="server" UpdateMode="Conditional" >
						<ContentTemplate>
							<init:RicerchePlusCtrl ID="rplMercatiUso" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati_Uso" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="Id" ServicePath="" AutoSelect="True" ServiceMethod="GetCompletionListUsi" AutoPostBack="True" />	
						</ContentTemplate>
						<Triggers>
							<asp:AsyncPostBackTrigger ControlID="rplMercato" EventName="ValueChanged" />
						</Triggers>
					</asp:UpdatePanel>
                    
		        </div>
		        <div>
		            <asp:Repeater ID="rptDettaglio" runat="server" OnItemDataBound="rptDettaglio_ItemDataBound">
		                <HeaderTemplate>
		                    <table>
		                        <colgroup width="25%"></colgroup>
		                        <colgroup width="25%"></colgroup>
		                        <colgroup width="25%"></colgroup>
		                        <colgroup width="25%"></colgroup>
		                        <tr class="intestazionetabella">
		                            <th colspan="4">Elenco posteggi</th>
		                        </tr>
		                </HeaderTemplate>
		                <ItemTemplate>
		                    <% if ( IndiceRecord % 4 == 0 ) {%>
                                <tr>
		                    <%} %>
		                        <td class="cellaPosteggio" valign="top">
		                            <uc1:Posteggio ID="Posteggio1" runat="server" CodiceUso='<%#Convert.ToInt32(CodiceUso)%>' MercatiD='<%#(Init.SIGePro.Data.Mercati_D)Container.DataItem%>' />
                                </td>
		                    <% if ( IndiceRecord % 4 == 3 ) {%>
		                    </tr>
		                    <%} %>
		                    <% IndiceRecord++; %>
		                </ItemTemplate>
		                <FooterTemplate>
		                        <tr class="intestazionetabella"><td colspan="4"></td></tr>
		                    </table>
		                </FooterTemplate>
		            </asp:Repeater>
		        </div>
                <div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click"/>
				</div>
            </fieldset>
		</asp:View>
	</asp:MultiView>
    
</asp:Content>
