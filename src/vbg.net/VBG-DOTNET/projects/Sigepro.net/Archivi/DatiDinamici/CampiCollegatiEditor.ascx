<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="CampiCollegatiEditor.ascx.cs" Inherits="Sigepro.net.Archivi.DatiDinamici.CampiCollegatiEditor" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="bs" Namespace="SIGePro.WebControls.Bootstrap" Assembly="SIGePro.WebControls" %>

<script type="text/javascript">
    vbg.ready(() => {

        function mostraConfermaEliminazioneCampo(id, nomeCampo) {
            const modal = document.getElementById('<%=bmRimuoviCampo.ClientID%>');

            const hfIdCampoCollegatoDaRimuovere = document.getElementById('<%=hfIdCampoCollegatoDaRimuovere.ClientID%>');
            const campoCollegatoDaRimuovere = modal.querySelector('.nome-campo-da-rimuovere');

            hfIdCampoCollegatoDaRimuovere.value = id;
            campoCollegatoDaRimuovere.innerText = nomeCampo;

            modal.show();
        }

        document.querySelectorAll('.rimuovi-campo-collegato').forEach(item => {
            item.addEventListener('click', (e) => {
                e.preventDefault();
                const id = item.dataset.id;
                const nomeCampo = item.innerText;

                mostraConfermaEliminazioneCampo(id, nomeCampo);
            })
        });
    });
</script>


<div class="comandi">
    <h4>Dipendenze campi <span class="badge"><asp:Literal runat="server" ID="ltrNumeroCampi"></asp:Literal></span></h4>

    <div class="dipendenze-campi">
        <asp:Repeater runat="server" ID="rptDipendenzeCampi">
            <HeaderTemplate>
                <ul>
            </HeaderTemplate>
            <ItemTemplate>
                <li>
                    <a href="#" class="rimuovi-campo-collegato" data-id='<%# DataBinder.Eval(Container.DataItem, "Id")%>'>
                        <%# DataBinder.Eval(Container.DataItem, "IdCampo") %>
                    </a>
                </li>
            </ItemTemplate>
            <FooterTemplate>
                </ul>
            </FooterTemplate>
        </asp:Repeater>
        <div class="lnk-aggiungi">
            <asp:LinkButton runat="server" ID="aggiungiCampiCollegati" CssClass="lnk-aggiungi" OnClick="aggiungiCampiCollegati_Click"><i class="fa fa-plus" aria-hidden="true"></i> Aggiungi dipendenza</asp:LinkButton>
        </div>
    </div>

</div>

<%if (MostraPopup)
    {%>
<bs:BootstrapModal runat="server" ID="bmAggiungiCampoCollegato" AlwaysVisible="true" Title="Aggiungi campo collegato"
    ShowOkButton="false" OnKoClicked="bmAggiungiCampoCollegato_KoClicked">
    <ModalBody>
        <fieldset>
            <init:LabeledDropDownList ID="ddlFiltroSoftware" runat="server" Descrizione="Software" Item-DataTextField="Descrizione" Item-DataValueField="Codice" />
            <init:LabeledTextBox ID="txtFiltroNomeCampo" runat="server" Descrizione="Campo" item-placeholder="Nome o etichetta campo" Item-Columns="60" Item-MaxLength="60" />

            <div>
                <asp:Button runat="server" ID="cmdCercaCampiCollegati" Text="Cerca" OnClick="cmdCercaCampiCollegati_Click" />
            </div>
        </fieldset>

        <asp:Repeater runat="server" ID="rptCampiCollegati">
            <HeaderTemplate>
                <table class="vbg-table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Etichetta</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
            </HeaderTemplate>
            <ItemTemplate>
                <tr>
                    <td><%#DataBinder.Eval(Container.DataItem, "IdCampo") %></td>
                    <td><%#DataBinder.Eval(Container.DataItem, "Etichetta") %></td>
                    <td>
                        <div>
                        <asp:LinkButton runat="server" ID="cmdAggiungiCampoCollegato" 
                            OnClick="cmdAggiungiCampoCollegato_Click" 
                            CommandArgument='<%# DataBinder.Eval(Container.DataItem, "Id")%>'
                            style="display: block;text-wrap: nowrap;">
                            <i class="fa fa-plus"></i> Aggiungi
                        </asp:LinkButton>
                        </div>
                    </td>
                </tr>
            </ItemTemplate>
            <FooterTemplate>
                </tbody>
                </table>
            </FooterTemplate>
        </asp:Repeater>
    </ModalBody>
</bs:BootstrapModal>
<%} %>

<bs:BootstrapModal runat="server" ID="bmRimuoviCampo" Title="Elimina campo collegato" OnOkClicked="bmRimuoviCampo_OkClicked">
    <ModalBody>
        <asp:HiddenField runat="server" ID="hfIdCampoCollegatoDaRimuovere" />
        Rimuovere il campo collegato <span id="campoCollegatoDaRimuovere" class="fw-bold nome-campo-da-rimuovere"></span>?
    </ModalBody>
</bs:BootstrapModal>
