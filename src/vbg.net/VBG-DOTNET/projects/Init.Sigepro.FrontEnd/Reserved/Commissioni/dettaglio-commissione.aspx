<%@ Page Title="Dettaglio commissione" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="dettaglio-commissione.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Commissioni.dettaglio_commissione" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    <style>
        .intestazione {
            display: flex;
            flex-wrap:wrap;
        }

        .intestazione fieldset{
            flex-grow: 1;
            min-width: 400px;
        }

        .intestazione fieldset:nth-child(even){
            padding-left: 12px;
        }
        .intestazione fieldset:nth-child(odd){
            padding-right: 12px;
        }

        .dati-intervento {
            width: 500px;
        }

        .lavori-pratica {
            max-width: 450px;
            white-space: nowrap;
            text-overflow: ellipsis;    
            overflow: hidden;
        }

        .azioni a {
            white-space: nowrap;
        }

        .dati-approvazione {
            padding: 6px;
            margin-top: 12px;
        }
    </style>

    <script type="text/javascript">
        $(() => {
            document.querySelectorAll('.approva-documento').forEach(item => {

                item.addEventListener('click', (e) => {
                    e.preventDefault();
                    const fileId = e.target.dataset.id;
                    const hash = e.target.dataset.hash;
                    const hlVerbale = e.target.closest('tr').querySelector('.verbale-commissione');
                    const dataCaricamento = e.target.closest('tr').querySelector('.data-caricamento').innerText;
                    const nomeFile = hlVerbale.innerText;
                    const url = hlVerbale.getAttribute('href');
                    const okButton = document.querySelector('#<%=bmApprovaVerbale.ClientID%> input.modal-ok-button');
                    const downloadFile = document.querySelector('#download-modal-accetta');
                    const placeholderNomeFile = document.querySelector('#placeholder-nome-file');

                    document.querySelector('#<%= hidIdVerbale.ClientID %>').value = fileId;
                    document.querySelector('#<%= lblHash.ClientID %>_inner').innerText = hash;
                    document.querySelector('#<%= lblDataCaricamento.ClientID %>_inner').innerText = dataCaricamento;
                    downloadFile.setAttribute('href', url);
                    downloadFile.innerText = nomeFile;
                    downloadFile.addEventListener('click', () => okButton.removeAttribute('disabled'));

                    placeholderNomeFile.innerHTML = `<b>${nomeFile}</b>`;

                    okButton.setAttribute('disabled', 'disabled');

                    $('#<%=bmApprovaVerbale.ClientID%>').modal('show');

                    return false;
                });

            });
        });
    </script>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="intestazione">
        <fieldset>
            <legend>
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-commissione.commissione.titolo">Commissione</ar:RisorsaTestualeLabel>
            </legend>
            <div class="row">
                <ar:LabeledLabel runat="server" ID="lblNumeroCommissione" Label="Numero" BtSize="Col6" />
                <ar:LabeledLabel runat="server" ID="lblDataCommissione" Label="Data" BtSize="Col6" />
            </div>
            <div class="row">
                <ar:LabeledLabel runat="server" ID="lblDescrizione" Label="Descrizione" BtSize="Col12" />
            </div>
            <div class="row">
                <ar:LabeledLabel runat="server" ID="lblOraInizio" Label="Ora inizio" BtSize="Col6" />
                <ar:LabeledLabel runat="server" ID="lblOraFine" Label="Ora fine" BtSize="Col6" />
            </div>
        </fieldset>

        <fieldset>
            <legend>
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-commissione.convocazioni.titolo">Convocazioni</ar:RisorsaTestualeLabel>
            </legend>

            <asp:Repeater runat="server" ID="rptConvocazioni">
                <HeaderTemplate>
                    <ul class="list-group">
                </HeaderTemplate>
                <ItemTemplate>
                    <li class="list-group-item <%# ((bool)Eval("Attiva"))? "active":"" %>">
                        <span class="badge"><%#Eval("DataOra") %></span>
                        <%#Eval("Descrizione") %>
                    </li>
                </ItemTemplate>

                <FooterTemplate></ul></FooterTemplate>
            </asp:Repeater>
        </fieldset>
    </div>

    <fieldset runat="server" id="fsDocumenti">
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-commissione.lista-documenti.titolo">Documenti</ar:RisorsaTestualeLabel>
        </legend>

        <asp:GridView runat="server" ID="gvDocumenti" GridLines="None" CssClass="table" 
            DataKeyNames="Id" AutoGenerateColumns="false">
            <Columns>
                <asp:BoundField HeaderText="Descrizione" DataField="Descrizione" />
                <asp:TemplateField HeaderText="File">
                    <ItemTemplate>
                        <asp:HyperLink runat="server" CssClass="verbale-commissione"
                            Text='<%# Eval("Nomefile") %>'
                            NavigateUrl='<%# GetUrlDownload((int)Eval("CodiceOggetto")) %>'
                        />
                        <small style="display:block">Hash: <%#Eval("Sha256") %>

                            <div runat="server" visible='<%#Eval("Approvato")%>'>
                                <div class="alert alert-success dati-approvazione">
                                Approvato il <%#Eval("DataApprovazione") %>
                                </div>
                            </div>
                        </small>

                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField HeaderText="Data" DataField="DataRegistrazione" ItemStyle-CssClass="data-caricamento" />
                <asp:TemplateField HeaderText="Azioni">
                    <ItemTemplate>
                        <asp:LinkButton runat="server" CssClass="approva-documento" CommandArgument="Approva" Text="Prendi visione/Approva" Visible='<%#!(bool)Eval("Approvato") %>'
                            data-hash='<%#Eval("Sha256") %>' data-id='<%#Eval("Id") %>'/>
                    </ItemTemplate>
                </asp:TemplateField>
            </Columns>

        </asp:GridView>
    </fieldset>


    <fieldset>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-commissione.lista-istanze.titolo">Istanze in discussione</ar:RisorsaTestualeLabel>
        </legend>

        <asp:GridView runat="server" ID="gvPratiche" GridLines="None" CssClass="table" 
            DataKeyNames="Uuid"
            AutoGenerateColumns="false" OnSelectedIndexChanged="gvPratiche_SelectedIndexChanged">
            <Columns>
                <asp:TemplateField HeaderText="Dettaglio Istanza" ItemStyle-CssClass="dati-istanza">
                    <ItemTemplate>
                        <%#Eval("Comune") %><br />
                        <%#Eval("NumeroData") %><br />
                        <%#Eval("DatiProtocollo") %><br />
                        <%#Eval("Richiedente") %><br />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Oggetto della pratica" ItemStyle-CssClass="dati-intervento">
                    <ItemTemplate>
                        <div class="intervento-pratica">
                            <%#Eval("Intervento") %>
                        </div>

                        <div class="lavori-pratica">
                            <%#Eval("DescrizioneLavori") %>
                        </div>

                        <div class="documenti-pratica">
                            <%#Eval("DocumentiSelezionati") %> documenti
                        </div>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField HeaderText="Parere" DataField="DescrizioneParere" />
                <asp:ButtonField HeaderText="Azioni" CommandName="Select"  ItemStyle-CssClass="azioni" Text="Seleziona <i class='fa fa-chevron-right'></i>" />

            </Columns>
        </asp:GridView>
    </fieldset>
    <div>
        <asp:LinkButton Text="Chiudi" CssClass="btn btn-default vbg-show-spinner" runat="server" ID="cmdChiudi"  OnClick="cmdChiudi_Click" />
    </div>

    <ar:BootstrapModal runat="server" ID="bmApprovaVerbale" Title="Presa visione/Approvazione documento" OkText="Approva" OnOkClicked="bmApprovaVerbale_OkClicked">
        <ModalBody>
            <div class="alert alert-warning">
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-commissione.popup.warning">
                    Attenzione! Stai per approvare un documento di una commissione, l'operazione non potrà essere annullata
                </ar:RisorsaTestualeLabel>
            </div>

            <div class="alert alert-info">
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-commissione.popup.testo-download-file">Per approvare il documento è necessario scaricare e visualizzare il file </ar:RisorsaTestualeLabel>
                <span id="placeholder-nome-file"></span>
            </div>
            <asp:HiddenField runat="server" ID="hidIdVerbale" />
            <div class="row">
                <ar:LabeledLabel runat="server" ID="lblHash" Label="Hash" BtSize="Col12" />
            </div>

            <div class="row">
                <ar:LabeledLabel runat="server" ID="lblDataCaricamento" Label="Data caricamento" BtSize="Col12" />
            </div>

            <div class="row">
                <div class="form-group col-md-12">
                    <label>Visualizza file</label>
                    <div class="form-control">
                        <a href="#" id="download-modal-accetta" target="_blank">nome-file.txt</a>
                    </div>
                </div>
            </div>

        </ModalBody>
    </ar:BootstrapModal>
</asp:Content>
