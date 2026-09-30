<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" ValidateRequest="false" AutoEventWireup="True" Inherits="Archivi_DatiDinamici_Dyn2ModelliCampi"
    Title="Campi della scheda" CodeBehind="Dyn2ModelliCampi.aspx.cs" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="bs" Namespace="SIGePro.WebControls.Bootstrap" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ Register Src="TagsEditor.ascx" TagPrefix="uc1" TagName="TagsEditor" %>

<asp:Content runat="server" ContentPlaceHolderID="headPagina">

    <style>
        select, input {
            margin: auto;
        }

        select {
            padding: 6px 12px;
            border-radius: 0;
            border: 1px solid #666;
            background-color: white;
        }

        tr.cella-campo > td:nth-child(4) {
            border-left: 4px solid greenyellow;
            border-bottom: 2px solid white;
            background-color: #f8ffed;
        }

        tr.cella-testo > td:nth-child(4) {
            border-left: 4px solid gainsboro;
            border-bottom: 2px solid white;
            background-color: #dff0d8;
        }

        tr.riga-multipla > td:nth-child(5) {
            position: relative;
        }

            tr.riga-multipla > td:nth-child(5)::after {
                content: "";
                border-right: 2px solid #31708f;
                /*border-top: 2px solid blue;
            border-bottom: 2px solid blue;*/
                width: 12px;
                display: block;
                position: absolute;
                top: 0;
                bottom: 0;
                left: -6px;
            }

        tr.riga-multipla > td:nth-child(5) {
            background-color: #d9edf7;
        }


        tbody > tr.riga-multipla.inizio-gruppo-multiplo > td:nth-child(5)::after {
            border-top: 2px solid #31708f;
        }

        tbody > tr.riga-multipla.fine-gruppo-multiplo > td:nth-child(5)::after {
            border-bottom: 2px solid #31708f;
        }



        .pannello-fonti {
        }

            .pannello-fonti label {
                display: block;
                font-weight: bold;
            }

            .pannello-fonti span {
                white-space: nowrap;
                display: block;
                margin-bottom: var(--half-padding);
                margin-right: var(--half-padding);
            }

        .tags-editor {

            margin-top: var(--half-padding);

            .tags-summary {
                .tags-summary_item {
                    display: inline-block;
                    background-color: #d9edf7;
                    border: 1px solid #31708f;
                    color: #31708f;
                    padding: 2px 6px;
                    border-radius: 4px;
                    margin-right: var(--half-padding);
                    margin-top: var(--half-padding);
                    font-size: 0.8em;
                    

                    &.hilight{
                        border-color: rebeccapurple;
                        background-color: rebeccapurple;
                        color: white;
                    }
                }

                .label {
                    font-weight: bold;
                    display: inline-block;
                    margin-right: var(--half-padding);
                }

                a {
                    color: #31708f;
                }
            }

            .popup-tags-list {

                .popup-tags-list_item {
                    display: flex;
                    justify-content: space-between;
                }

                a {
                    color: var(--accent-color);
                }
            }
        }
    </style>

    <script type="text/javascript">

        function MostraDettaglioCampo(idCampo) {
            var url = "Dyn2Campi.aspx?Popup=true&Token=<%=Token %>&Software=<%=Software%>&IdCampo=" + idCampo;
            var winName = "dettagliCampo";
            var feats = "resizable,left=0,top=0,screenX=0,screenY=0,scrollbars=yes";

            const w = window.open(url, winName, feats);
            setTimeout(() => w.resizeTo(Math.max(screen.availWidth / 2, 900), screen.availHeight), 500);


            window.addEventListener('campo-modificato', (e) => {
                w.close();
            });
        }

        function MostraAnteprima(idModello) {
            var url = "Dyn2ModelliPreview.aspx?Token=<%=Token %>&IdModello=" + idModello;
            var winName = "anteprima";
            var feats = "menubar=no,status=no,width=1000,height=800,scrollbars=yes";
            window.open(url, winName, feats);
        }
		<%if (!String.IsNullOrEmpty(ScrollTo))
        {%>

        $(function onLoad() {
            function scrollToElement(aid) {
                var el = $("a." + aid);
                $('html,body').animate({ scrollTop: el.offset().top }, 'slow');
            }

            scrollToElement('<%=ScrollTo%>');
        })

        <%}%>

        vbg.ready(() => {

            document.querySelectorAll("*[data-tag]").forEach(item => {
                item.addEventListener('mouseover', () => {
                    const tag = item.dataset.tag;
                    document.querySelectorAll(`*[data-tag='${tag}']`).forEach(tagItem => tagItem.classList.add('hilight'));
                });
                item.addEventListener('mouseout', () => {
                    const tag = item.dataset.tag;
                    document.querySelectorAll(`*[data-tag='${tag}']`).forEach(tagItem => tagItem.classList.remove('hilight'));
                });
            });

        })
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <asp:ScriptManager ID="ScriptManager1" runat="server">
    </asp:ScriptManager>



    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
        <asp:View runat="server" ID="listaView">

            <script type="text/javascript">
                vbg.ready(() => {

                    const txtFonteInterna = document.getElementById('<%=txtFonteInterna.ClientID%>').querySelector("input");
                    const hidIdRigaModello = document.getElementById('<%=hidIdRigaModello.ClientID%>');

                    txtFonteInterna.setAttribute("list", "fontiInterneDataList");

                    document.querySelectorAll('.edit-fonte-dati').forEach((it) => {
                        it.addEventListener('click', (e) => {
                            const target = e.target;
                            const bm = '<%=bmFonteDati.ClientID%>';
                            const id = target.dataset.id;

                            const fonteInterna = target.closest('div').querySelector('.fonte-interna')?.innerText ?? '';
                            const nomeCampo = target.closest('div').querySelector('input[name="hidNomeCampoSrc"]')?.value;


                            hidIdRigaModello.value = id;
                            txtFonteInterna.value = fonteInterna;

                            const modal = document.getElementById(bm);
                            const hidNomeCampoDest = modal.querySelector('#hidNomeCampo');

                            hidNomeCampoDest.value = nomeCampo;

                            modal.querySelector('#usa-nome-campo-interna').addEventListener('click', (e) => {
                                txtFonteInterna.value = hidNomeCampoDest.value;
                                e.preventDefault();
                            });

                            modal.show();
                        });
                    });

                    // La fonte interna può contenere solo lettere, numeri, underscore e punti
                    // txtFonteInterna.style.textTransform = 'uppercase';
                    // txtFonteInterna.addEventListener('keyup', () => txtFonteInterna.value = txtFonteInterna.value.toLocaleUpperCase());
                });
            </script>

            <init:GridViewEx runat="server" ID="gvLista" AutoGenerateColumns="False" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DatabindOnFirstLoad="True"
                DataKeyNames="Id" OnDataBound="gvLista_DataBound"
                OnRowDataBound="gvLista_RowDataBound">
<%--                <AlternatingRowStyle CssClass="RigaAlternata" />
                <RowStyle CssClass="Riga" />
                <HeaderStyle CssClass="IntestazioneTabella" />
                <EmptyDataRowStyle CssClass="NessunRecordTrovato" />--%>
                <EmptyDataTemplate>
                    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
                </EmptyDataTemplate>
                <Columns>
                    <asp:TemplateField HeaderText="Id">
                        <ItemTemplate>

                            <asp:LinkButton runat="server" CommandName="Select" CommandArgument='<%#Eval("Id") %>' Text='<%#Eval("Id") %>' CssClass='<%#Eval("Id") %>' />
                        </ItemTemplate>
                    </asp:TemplateField>
                    <asp:BoundField DataField="Posverticale" HeaderText="Riga" SortExpression="Posverticale" />
                    <asp:BoundField DataField="Posorizzontale" HeaderText="Colonna" SortExpression="Posorizzontale" />

                    <asp:TemplateField HeaderText="Campo dinamico / Testo" SortExpression="CampoDinamico">
                        <ItemTemplate>
                            <asp:Panel runat="server" Visible='<%# (bool)Eval( "IsCampoDinamico")%>'>
                                <a href='javascript:MostraDettaglioCampo(<%# Eval( "IdCampoDinamico") %>)'>
                                    <asp:Label runat="server" Text='<%# Eval("NomeCampo") %>' ID="Label1" /><%#Eval("IdCampoDinamico", " [{0}]") %>
                                </a>
                                <br />
                                <small><%#Eval("Tipodato", "{0} - ") %><%#Eval("Etichetta", "<i>{0}</i>") %></small>
                            </asp:Panel>

                            <asp:Panel runat="server" Visible='<%# ((bool)Eval( "IsCampoDinamico")) == false%>'>
                                <%#Eval("TestoCampoTestuale") %>
                            </asp:Panel>

                            <uc1:TagsEditor runat="server" ID="tagsEditor" OnTagsSaved="TagsEditor1_TagsSaved" DataListId="tags-list" />
                        </ItemTemplate>
                    </asp:TemplateField>

                    <asp:TemplateField HeaderText="Tipo riga" SortExpression="FlgMultiplo">
                        <ItemTemplate>
                            <asp:DropDownList runat="server" ID="ddlMultiplo"
                                AutoPostBack="true"
                                OnSelectedIndexChanged="ddlMultiplo_SelectedIndexChanged">
                                <asp:ListItem Text="Riga singola" Value="0" />
                                <asp:ListItem Text="Riga multipla" Value="1" />
                                <asp:ListItem Text="Multipla non modificabile" Value="2" />
                            </asp:DropDownList>

                            <asp:HiddenField runat="server" ID="hidRiga" Value='<%# Eval("Posverticale") %>' />
                        </ItemTemplate>
                    </asp:TemplateField>

                    <asp:TemplateField SortExpression="FlgSpezzaTabella">
                        <HeaderTemplate>
                            <div title="Gli elementi successivi ad un elemento selezionato verranno inseriti in un nuovo raggruppamento. 
                                        Utilizzare questa funzionalità per organizzare lo spazio occupato dai controlli">
                                Termina tabella 
                            </div>
                        </HeaderTemplate>
                        <ItemTemplate>

                            <asp:CheckBox runat="server" ID="chkSpezzaTabella"
                                AutoPostBack="true"
                                Checked='<%# (bool)Eval( "FlgSpezzaTabella")%>'
                                OnCheckedChanged="chkSpezzaTabella_CheckedChanged" />

                        </ItemTemplate>
                    </asp:TemplateField>

                    <asp:TemplateField HeaderText="Fonte dati">
                        <ItemTemplate>
                            <asp:Panel CssClass="pannello-fonti" runat="server" Visible='<%# Eval( "SupportaOnceOnly")%>'>
                                <asp:Panel runat="server" Visible='<%# !String.IsNullOrEmpty(Eval( "FonteInterna").ToString())%>'>
                                    <label>Fonte interna</label>
                                    <span class="fonte-interna"><%#Eval("FonteInterna") %></span>
                                </asp:Panel>
                                <input type="hidden" name="hidNomeCampoSrc" value='<%#Eval("NomeCampo") %>' />
                                <input type="button" value="Modifica" class="edit-fonte-dati" data-id='<%#Eval("Id") %>' />
                            </asp:Panel>
                        </ItemTemplate>
                    </asp:TemplateField>
                </Columns>
            </init:GridViewEx>

            <bs:BootstrapModal runat="server" ID="bmFonteDati" Title="Modifica fonte dati" OnOkClicked="bmFonteDati_OkClicked">
                <ModalBody>
                    <fieldset>
                        <input type="hidden" id="hidNomeCampo" />
                        <asp:HiddenField runat="server" ID="hidIdRigaModello" />
                        <init:LabeledTextBox ID="txtFonteInterna" FilterInput="UPPER_ALPHAS_NUMBERS_DOTS_UNDERSCORE" runat="server" Descrizione="Fonte interna" Item-Columns="100" Item-MaxLength="500" />
                        <div>
                            <label>&nbsp;</label>
                            <span><a href="#" id="usa-nome-campo-interna">Usa nome campo</a></span>
                        </div>
                    </fieldset>
                </ModalBody>
            </bs:BootstrapModal>

            <asp:Repeater runat="server" ID="rptDataList">
                <HeaderTemplate>
                    <datalist id="fontiInterneDataList">
                </HeaderTemplate>
                <ItemTemplate>
                    <option><%#DataBinder.Eval(Container, "DataItem") %></option>
                </ItemTemplate>
                <FooterTemplate></datalist></FooterTemplate>
            </asp:Repeater>

            <asp:Repeater runat="server" ID="rptTagsDataList">
                <HeaderTemplate>
                    <datalist id="tags-list">
                </HeaderTemplate>
                <ItemTemplate>
                    <option><%#DataBinder.Eval(Container, "DataItem") %></option>
                </ItemTemplate>
                <FooterTemplate></datalist></FooterTemplate>
            </asp:Repeater>

            <div class="Bottoni mt-2">
                <init:SigeproButton runat="server" ID="cmdNuovo" Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
                <init:SigeproButton runat="server" ID="cmdPreview" Text="Anteprima" IdRisorsa="ANTEPRIMAMODELLO" />
                <init:SigeproButton runat="server" ID="cmdFormule" Text="Anteprima" IdRisorsa="FORMULE" OnClick="cmdFormule_Click" />
                <init:SigeproButton runat="server" ID="cmdRicalcolaNumerazione" Text="Ricalcola numerazione" IdRisorsa="RICALCOLANUMERAZIONE" OnClick="cmdRicalcolaNumerazione_Click" />
                <asp:Button runat="server" ID="cmdVerificaCampi" Text="Verifica HTML campi" OnClick="cmdVerificaCampi_Click" />
                <init:SigeproButton runat="server" ID="ImageButton1" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />
            </div>


        </asp:View>
        <asp:View runat="server" ID="dettaglioView">
            <script type="text/javascript">
                function impostaCampo(datiCampo) {
                    var el = $find("autoComlpeteCampoDinamico");

                    el.get_element().value = datiCampo.codice;
                    $get(el.get_DescriptionControlID()).value = datiCampo.descrizione;
                }


                function CreaCampo() {

                    var url = '<%= ResolveClientUrl( "~/Archivi/DatiDinamici/Dyn2Campi.aspx?Popup=true&Token=" ) + Token + "&Software=" + Software + "&PopupCreaNuovo=true"%>';
                    var feats = "resizable,left=0,top=0,screenX=0,screenY=0,scrollbars=yes";

                    const w = window.open(url, 'nuovoControllo', feats, "POS");
                    setTimeout(() => w.resizeTo(Math.max(screen.availWidth / 2, 900), screen.availHeight), 500);

                    window.addEventListener('campo-creato', (e) => {
                        impostaCampo(e.detail);
                        w.close();
                    });

                    return false;
                }

                function ConfermaEliminazione() {
                    return confirm("Eliminare il record selezionato?");
                }

                function MostraDettaglioCampo(idCampo) {
                    var url = "Dyn2Campi.aspx?Popup=true&Token=<%=Token %>&Software=<%=Software%>&IdCampo=" + idCampo;
                    var winName = "dettagliCampo";
                    var feats = "resizable,left=0,top=0,screenX=0,screenY=0,scrollbars=yes";

                    const w = window.open(url, winName, feats);
                    setTimeout(() => w.resizeTo(Math.max(screen.availWidth / 2, 900), screen.availHeight), 500);


                    window.addEventListener('campo-modificato', (e) => {
                        w.close();
                    });

                    return false;
                }
            </script>

            <fieldset>
                <init:LabeledLabel runat="server" ID="lblId" Descrizione="Codice" />
                <init:LabeledIntTextBox ID="txtPosVerticale" HelpControl="hdPosVerticale" runat="server" Descrizione="*Riga" Item-Columns="4" Item-MaxLength="4" />
                <init:HelpDiv runat="server" ID="hdPosVerticale">
					Se lasciato vuoto verrà utilizzata la prima riga disponibile
                </init:HelpDiv>
                <init:LabeledIntTextBox ID="txtPosOrizzontale" HelpControl="hdPosOrizzontale" runat="server" Descrizione="*Colonna" Item-Columns="2" Item-MaxLength="1" />
                <init:HelpDiv runat="server" ID="hdPosOrizzontale">
					Se lasciato vuoto verrà utilizzata la prima colonna disponibile della riga indicata
                </init:HelpDiv>
                <init:LabeledDropDownList ID="ddlTipoCampo" Descrizione="Tipo campo" runat="server" OnValueChanged="ddlTipoCampo_ValueChanged" Item-AutoPostBack="true" />
                <asp:Panel runat="server" ID="pnlCampoTesto">
                    <init:LabeledDropDownList ID="ddlBaseTipoTesto" runat="server" Descrizione="Tipo testo" Item-DataTextField="Tipotesto" Item-DataValueField="Id" />
                    <init:LabeledTextBox ID="txtTestoEsteso" runat="server" Item-TextMode="MultiLine" Descrizione="Testo" Item-Columns="100" Item-Rows="10" />
                </asp:Panel>
                <asp:Panel runat="server" ID="pnlCampoDinamico">
                    <asp:Label runat="server" ID="Label23" AssociatedControlID="rplCampoDinamico" Text="Campo dinamico" />
                    <init:RicerchePlusCtrl ID="rplCampoDinamico" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista"
                        CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10"
                        DataClassType="Init.SIGePro.Data.Dyn2Campi" DescriptionPropertyNames="Nomecampo" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150"
                        MinimumPrefixLength="1" ServiceMethod="GetCompletionList" TargetPropertyName="Id" ServicePath="" AutoSelect="True"
                        BehaviorID="autoComlpeteCampoDinamico" />
                    <init:SigeproButton runat="server" ID="cmdCreaCampo" OnClientClick="return CreaCampo();" IdRisorsa="NUOVO" />
                </asp:Panel>
                <%--				<asp:Panel runat="server" ID="pnlSolaLettura" Visible="false">
					<div>
						<asp:Label runat="server" ID="Label200" AssociatedControlID="lblTipoCampoRo" Text="Tipo campo" />
						<asp:Label runat="server" ID="lblTipoCampoRo" />
					</div>
					
					<div>
						<asp:Label runat="server" ID="Label3" AssociatedControlID="lblCampoDinamicoRo" Text="Campo dinamico" />
						<asp:Label runat="server"  ID="lblCampoDinamicoRo" />
					</div>
				</asp:Panel>--%>
				&nbsp;&nbsp;
				<div class="Bottoni mt-2">
                    <init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
                    <asp:Button runat="server" ID="cmdModificaDettagli" Text="Dettagli Campo" />
                    <init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" OnClientClick="return ConfermaEliminazione();" />
                    <init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
                </div>
            </fieldset>
        </asp:View>
    </asp:MultiView>
</asp:Content>
