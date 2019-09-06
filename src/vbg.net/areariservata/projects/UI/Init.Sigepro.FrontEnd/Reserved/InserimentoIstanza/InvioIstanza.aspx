<%@ Page Title="Invio Istanza" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="InvioIstanza.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.InvioIstanza" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <asp:MultiView runat="server" ID="mvInvioIstanza">

        <asp:View runat="server" ID="allegaFileView">

            <h3>
                <asp:Literal runat="server" ID="ltrSottotitoloInvio">
							Scaricare, firmare digitalmente e ricaricare il documento
                </asp:Literal>
            </h3>

            <p>
                <asp:Literal runat="server" ID="ltrDescrizioneFaseInvio">
                        Descrizione fase invio
                </asp:Literal>
            </p>

            <!-- File da scaricare e firmare digitalmente -->
            <div>
                <ul>
                    <li>
                        <asp:HyperLink runat="server" ID="hlModelloDomanda">
                            <i class="fa fa-file-pdf-o"></i>
                            <asp:Literal runat="server" ID="ltrNomeFiledaScaricare" Text="Istanza" />
                        </asp:HyperLink>
                    </li>
                </ul>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <asp:Literal runat="server"
                        ID="Label4"
                        Text="Selezionare il file da inviare" />
                </div>
                <div class="panel-body">
                    <asp:FileUpload runat="server" ID="fuRiepilogo" />

                    <div class="bottoni">
                        <asp:Button runat="server"
                            ID="cmdUploadDomanda"
                            Text="Allega"
                            CssClass="btn btn-primary"
                            OnClick="cmdUploadDomanda_Click" />
                    </div>
                </div>


            </div>
        </asp:View>

        <asp:View runat="server" ID="firmaEInviaView">
            <script type="text/javascript">
                $(function () {
                    var pannelloDichiarazione = $(".pannello-dichiarazione");

                    if (pannelloDichiarazione.length) {

                        $('.invia').fadeToggle($('.check-dichiarazioni > input').is(':checked'));

                        // $('.soggetti-firmatari').width(pannelloDichiarazione.width() + 20);

                        $('.check-dichiarazioni > input').click(function () {

                            $('.invia').fadeToggle($('.check-dichiarazioni > input').is(':checked'), function () {
                                window.scrollTo(0, document.body.scrollHeight);
                            });

                        });
                    }

                    $('.invia').click(onInvioClick);
                });

                function onInvioClick(e) {
                    nascondiBottoni();
                    mostraMessaggio();
                }

                function nascondiBottoni() {
                    $('.bottoni').css('display', 'none')
                }

                function mostraMessaggio() {
                    $('.modal-invio-istanza-in-corso').modal('show');
                }
            </script>

            <%if (MostraGrigliaSottoscrittori)
                {%>

            <h3>
                <asp:Literal runat="server" ID="ltrIntestazioneSottoscrittori" Text="L'istanza deve essere firmata da:" />
            </h3>

            <div>
                <asp:GridView AutoGenerateColumns="false"
                    runat="server"
                    ID="gvSoggettiFirmatari"
                    CssClass="soggetti-firmatari table"
                    GridLines="None">
                    <Columns>
                        <asp:TemplateField HeaderText="Nominativo">
                            <ItemTemplate>
                                <asp:Literal runat="server" ID="ltrNome" Text='<%# DataBinder.Eval(Container,"DataItem") %>' />
                            </ItemTemplate>
                        </asp:TemplateField>
                        <asp:BoundField HeaderText="In qualità di" DataField="TipoSoggetto" />
                    </Columns>
                </asp:GridView>
            </div>

            <%} %>

            <asp:Panel runat="server" ID="pnlSoggettiNonSottoscriventi">
                <h3>
                    <asp:Literal runat="server" ID="ltrSoggetiNonSottoscrittori" Text="I soggetti che non sottoscrivono sono" />
                </h3>
                <asp:GridView runat="server" AutoGenerateColumns="false" ID="gvSoggettiNonSottoscriventi" CssClass="table" GridLines="None">
                    <Columns>
                        <asp:TemplateField HeaderText="Nominativo">
                            <ItemTemplate>
                                <asp:Literal runat="server" ID="ltrNome" Text='<%# DataBinder.Eval(Container,"DataItem") %>' />
                            </ItemTemplate>
                        </asp:TemplateField>
                        <asp:BoundField HeaderText="In qualità di" DataField="TipoSoggetto" />
                    </Columns>
                </asp:GridView>
            </asp:Panel>


            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">File caricato</h3>
                </div>
                <div class="panel-body">

                    <asp:Literal runat="server" ID="lblErroreRiepilogo">
                            <div class="alert alert-danger" role="alert">
                                Attenzione, il file non è firmato digitalmente
                            </div>
                    </asp:Literal>

                    <asp:HyperLink runat="server" ID="hlRiepilogoDomanda" Text="Visualizza il file caricato" /><br />

                    <%if (!String.IsNullOrEmpty(IntestazioneBottoniFirma))
                        {%>
                    <div class="intestazione-bottoni-firma">
                        <asp:Literal runat="server" ID="ltrIntestazioneBottoniFirma" />
                    </div>
                    <%} %>

                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdFirma" Text="Firma online"
                            OnClick="cmdFirma_Click" />

                        <asp:Button runat="server" ID="cmdFirmaCidPin" Text="Firma con CID/PIN"
                            OnClick="cmdFirmaCidPin_Click" />

                        <asp:Button runat="server" ID="cmdFirmaGrafometrica" Text="Firma grafometrica"
                            OnClick="cmdFirmaGrafometrica_Click" />

                        <asp:Button runat="server" ID="cmdFirnaConDispositivoEsterno" Text="Firma con dispositivo esterno"
                            OnClick="cmdFirnaConDispositivoEsterno_Click" />

                        <asp:Button runat="server" ID="cmdAllegaAltroFile" OnClick="cmdFirnaConDispositivoEsterno_Click" CssClass="allegato-ok" Text="Sostituisci il file allegato" />
                    </div>

                </div>
            </div>



            <asp:Panel runat="server" ID="pnlDichiarazione" CssClass="panel panel-default pannello-dichiarazione">
                <div class="panel-body">
                    <asp:Label runat="server" ID="lblDichiarazione">Dichiarazione da accettare</asp:Label><br />
                </div>
                <div class="panel-footer">
                    <asp:CheckBox runat="server" ID="chkDichiarazione" Text="Check dichiarazione" TextAlign="right" CssClass="check-dichiarazioni checkbox" />
                </div>
            </asp:Panel>

            <div class="bottoni">
                <asp:Button runat="server" ID="cmdInviaDomanda" OnClick="cmdInviaDomanda_Click" CssClass="allegato-ok invia" Text="Trasferisci l'istanza al comune" />
            </div>

        </asp:View>

        <asp:View runat="server" ID="erroreInvioView">

            <div class="alert alert-danger" role="alert">
                <asp:Literal runat="server" ID="lblErroreInvio" />
            </div>
        </asp:View>


    </asp:MultiView>

</asp:Content>
