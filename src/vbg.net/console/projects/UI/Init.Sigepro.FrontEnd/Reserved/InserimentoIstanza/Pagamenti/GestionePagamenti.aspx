<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="GestionePagamenti.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.GestionePagamenti" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="uc1" TagName="GrigliaPagamentoOneri" Src="~/Reserved/InserimentoIstanza/Pagamenti/GrigliaPagamentoOneri.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <style>
        #tabellaEndo .descrizioneEndo, #tabellaEndo .descrizioneIntervento {
            font-size: 0.8em;
            display: block;
            margin-top: 5px;
            margin-left: 10px;
        }

        #tabellaEndo .rigaTotaleImporti > td {
            text-align: right;
            font-weight: bold;
        }

        #tabellaEndo .helpNoteOnereRow {
            text-align: center;
        }
    </style>
    <script type="text/javascript">

        require(['app/pagamenti/gestionePagamenti', 'jquery', 'jquery.ui'], function (GestionePagamenti, $) {

            $(function () {
                var gp = new GestionePagamenti();

                gp.init();
            });
        });
    </script>

    <table id="tabellaEndo" class="table">
        <uc1:GrigliaPagamentoOneri runat="Server" ID="grigliaOneriIntervento" />
        <uc1:GrigliaPagamentoOneri runat="Server" ID="grigliaOneriEndo" />
        <tr class="rigaTotaleImporti">
            <td colspan="6">
                <b>TOTALE DA PAGARE</b>
            </td>
            <td>€ <span id="totaleDaPagare"></span>
            </td>
        </tr>
    </table>

    <div id="testoNote">
    </div>

    <div class="panel panel-primary" id="totaleDaPagareOnline">
        <div class="panel-heading">
            <h3 class="panel-title">Totale da pagare online
            </h3>
        </div>

        <div class="panel-body">
            <div id="riepilogoOneriOnline">
            </div>
        </div>

    </div>

    <div class="panel panel-primary" id="dichiarazioneAssenzaOneri">

        <div class="panel-heading">
            <h3 class="panel-title">Dichiarazione assenza oneri da pagare
            </h3>
        </div>

        <div class="panel-body">
            <asp:CheckBox runat="server" ID="chkAssenzaOneri" class="chkAssenzaOneri" OnCheckedChanged="chkAssenzaOneri_CheckedChanged" />
            <span style="font-weight: bold">
                <%= TestoDichiarazioneAssenzaOneri%></span>
        </div>
    </div>



    <div class="panel panel-primary" id="bloccoCaricamentoBollettino">
        <div class="panel-heading">
            <h3 class="panel-title"><%=TitoloCaricamentoBollettino %>
            </h3>
        </div>
        <div class="panel-body">
            <div id="riepilogoOneriPagati">
            </div>

            <asp:MultiView runat="server" ID="mvCaricamentoBollettino" ActiveViewIndex="0">
                <asp:View runat="server" ID="uploadView">

                    <%=DescrizioneCaricamentoBollettino %>

                    <div class="form-group">
                        <asp:Label runat="server" ID="lblUploadFile" Text="Seleziona il file da allegare:"
                            AssociatedControlID="fuCaricaFile" />

                        <asp:FileUpload runat="server" ID="fuCaricaFile" CssClass="form-control" />
                    </div>
                    <p class="bottoni">
                        <asp:Button runat="server" ID="cmdUpload" Text="Allega file" OnClick="cmdUpload_Click" OnClientClick="onCaricamentoAllegato()" />
                    </p>
                </asp:View>
                <asp:View runat="server" ID="dettagliouploadView">
                    <div class="descrizioneStep">
                        <%=DescrizioneCaricamentoBollettinoEffettuato %>
                    </div>

                    <div class="form-group">
                        <asp:Label runat="server" ID="lblFileCaricato" Text="File allegato:" AssociatedControlID="hlFileCaricato" />
                        <asp:HyperLink runat="server" ID="hlFileCaricato" CssClass="form-control" />
                    </div>

                    <asp:Literal runat="server" ID="lblErroreFirma">
                            <div class="alert alert-danger" role="alert">
							    &nbsp;Attenzione, il file non è stato firmato digitalmente
                            </div>
                    </asp:Literal>

                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdFirma" Text="Firma" OnClick="cmdFirma_Click" />
                        <asp:Button runat="server" ID="cmdRimuovi" Text="Rimuovi file" OnClick="cmdRimuovi_Click"
                            OnClientClick="return confirm('Rimuovere il file selezionato\?')" />
                    </div>
                </asp:View>
            </asp:MultiView>
        </div>

    </div>

</asp:Content>
