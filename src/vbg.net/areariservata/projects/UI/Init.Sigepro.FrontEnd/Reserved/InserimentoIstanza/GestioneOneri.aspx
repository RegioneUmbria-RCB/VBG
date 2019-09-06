<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="GestioneOneri.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneOneri" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="uc1" TagName="GrigliaOneri" Src="~/Reserved/InserimentoIstanza/GestioneOneri_GrigliaOneri.ascx" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


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

    <%=LoadScripts(new[]{
        "~/js/app/pagamenti/tabellariepilogooneri.js",
        "~/js/app/pagamenti/gestionePagamenti.js",
    }) %>

    <script type="text/javascript">

        function onCaricamentoAllegato() {
            $('.modal-caricamento-file-in-corso').modal('show');
        }

        $(function () {
            var gp = new GestionePagamenti(),
                fuCaricaFile = $("#<%=fuCaricaFile.ClientID%>"),
                cmdUpload = $('#<%=cmdUpload.ClientID%>');

            gp.init();


            fuCaricaFile.on("change", function () {
                if ($(this).val()) {
                    cmdUpload.click();
                }
            });

        });

    </script>

    <table id="tabellaEndo" class="table">
        <uc1:GrigliaOneri runat="Server" ID="grigliaOneriIntervento" />
        <tr>
            <td colspan="7">&nbsp;</td>
        </tr>
        <uc1:GrigliaOneri runat="Server" ID="grigliaOneriEndo" />
        <tr class="rigaTotaleImporti">
            <td colspan="6">
                <b>TOTALE DA PAGARE</b>
            </td>
            <td>€ <span id="totaleDaPagare"></span>
            </td>
        </tr>
    </table>

<%--    <div id="testoNote">
    </div>--%>

    <ar:BootstrapModal runat="server" ID="testoNote" ExtraCssClass="modal-note-oneri" Title="Note onere"></ar:BootstrapModal>

    <div class="panel panel-primary" id="dichiarazioneAssenzaOneri">

        <div class="panel-heading">
            <h3 class="panel-title">
                Dichiarazione assenza oneri da pagare
            </h3>
        </div>


        <div class="panel-body">
            <asp:CheckBox runat="server" ID="chkAssenzaOneri" OnCheckedChanged="chkAssenzaOneri_CheckedChanged" />
            <span>
                <%= TestoDichiarazioneAssenzaOneri%>
            </span>
        </div>
    </div>


    <div class="panel panel-primary" id="bloccoCaricamentoBollettino">

        <div class="panel-heading">
            <h3 class="panel-title">
                <%=TitoloCaricamentoBollettino %>
            </h3>
        </div>

        <div class="panel-body">
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

                    
                    <asp:Button runat="server" ID="cmdFirma" Text="Firma" OnClick="cmdFirma_Click" CssClass="btn btn-primary" />
                    <asp:Button runat="server" ID="cmdRimuovi" Text="Rimuovi file" OnClick="cmdRimuovi_Click" CssClass="btn btn-default"
                        OnClientClick="return confirm('Rimuovere il file selezionato\?')" />
                    
                </asp:View>
            </asp:MultiView>
        </div>
    </div>
</asp:Content>
