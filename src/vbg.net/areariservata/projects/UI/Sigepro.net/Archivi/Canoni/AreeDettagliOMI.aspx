<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="AreeDettagliOMI.aspx.cs" Inherits="Sigepro.net.Archivi.Canoni.AreeDettagliOMI" Title="Determinazione valori OMI in base alle zone"%>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<script language="javascript" type="text/javascript">
    function ClosePopUp()
    {
        window.opener.location.reload();
        self.close();
    }
</script>
<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="dettaglioView">
            <fieldset>
                <asp:Label runat="server" ID="Label2" Text="*Zona" AssociatedControlID="rplAree"/>
                <init:RicerchePlusCtrl runat="server" ID="rplAree" ColonneCodice="5" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Aree" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" ServicePath="~/WebServices/WSSiGePro/RicerchePlus.asmx" AutoSelect="True" ServiceMethod="GetCompletionList" DescriptionPropertyNames="DENOMINAZIONE" RicercaSoftwareTT="False" TargetPropertyName="CODICEAREA"/>
                <init:LabeledDoubleTextBox runat="server" ID="txCoefficienteOMI" Descrizione="*Coefficiente OMI" Item-Columns="5" Item-MaxLength="5" />
                <div>
                    <init:SigeproButton runat="server" ID="cmdSalva"  Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click"/>
                    <init:SigeproButton runat="server" ID="cmdChiudi"  Text="Chiudi" IdRisorsa="CHIUDI"/>
                </div>
            </fieldset>
        </asp:View>
    </asp:MultiView>
</asp:Content>