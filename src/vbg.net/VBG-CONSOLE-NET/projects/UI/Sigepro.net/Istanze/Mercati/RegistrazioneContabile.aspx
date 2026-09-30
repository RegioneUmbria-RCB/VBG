<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" CodeBehind="RegistrazioneContabile.aspx.cs" Inherits="Sigepro.net.Istanze.Mercati.RegistrazioneContabile" Title="Gestione contabilità delle presenze" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<script type="text/javascript">
function RicalcolaTotale( numPresenze, objImpUnitario, clientIdImpTotale )
{
    var importo = objImpUnitario.value.replace(",",".");
    var totale =  numPresenze * importo;
    document.getElementById(clientIdImpTotale).value = totale.toString().replace(".",",");
}
</script>
<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="dettaglioView">
		    <fieldset>
		        <legend>Riepilogo</legend>
                <div>
			        <asp:Label runat="server" ID="Label5" Text="*Mercato" AssociatedControlID="rplMercato"/>
			        <init:RicerchePlusCtrl ID="rplMercato" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="CodiceMercato" ServicePath="~/WebServices/WsSIGePro/RicerchePlus.asmx" AutoSelect="True" ServiceMethod="GetCompletionList" ReadOnly="true"/>
			    </div>
			    <div>
					<asp:Label runat="server" ID="Label9" Text="*Giorno" AssociatedControlID="rplMercatiUso"/>
                    <init:RicerchePlusCtrl ID="rplMercatiUso" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati_Uso" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="Id" ServicePath="" AutoSelect="True" ServiceMethod="GetCompletionListUsi" ReadOnly="true"/>
			    </div>
                <asp:Repeater ID="rptPresenze" runat="server" OnItemDataBound="rptPresenze_ItemDataBound">
			        <HeaderTemplate>
	                    <table border="0" cellspacing="0" cellpadding="0">
                            <colgroup width="10%"></colgroup>
                            <colgroup width="60%"></colgroup>
                            <colgroup width="15%"></colgroup>
                            <colgroup width="15%"></colgroup>
                            <tr class="intestazionetabella">
		                        <th>Posteggio</th>
		                        <th>Occupante</th>
		                        <th>Presenze*Importo</th>
		                        <th>Totale</th>
		                    </tr>
			        </HeaderTemplate>
			        <ItemTemplate>
			            <tr>
			                <td>
			                    <init:IntTextBox ID="txIdPosteggio" runat="server" Columns="3" Visible="false" />
			                    <asp:Label ID="lblCodicePosteggio" runat="server" Text="" />
			                </td>
			                <td>
			                    <init:IntTextBox ID="txCodiceAnagrafe" runat="server" Columns="3" Visible="false" />
			                    <asp:Label ID="lblOccupante" runat="server" Text="" />
			                </td>
			                <td>
			                    <asp:Label ID="lblPresenze" runat="server" Text="" />&nbsp;*&nbsp;<init:FloatTextBox runat="server" ID="txImportoUnitario" Columns="5"/>
			                </td>
			                <td>
			                    <init:FloatTextBox runat="server" ID="txImportoTotale" Columns="5"/>
			                </td>
			            </tr>
			        </ItemTemplate>
                    <FooterTemplate>
                            <tr class="intestazionetabella"><td colspan="4"></td></tr>
                        </table>
                    </FooterTemplate>
			    </asp:Repeater>
                <div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdRegistra" Text="Registra" IdRisorsa="REGISTRA"/>
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI"/>
				</div>
		    </fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>
