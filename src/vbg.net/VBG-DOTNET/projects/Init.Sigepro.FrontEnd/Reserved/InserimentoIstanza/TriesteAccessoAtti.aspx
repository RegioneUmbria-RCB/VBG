<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="TriesteAccessoAtti.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TriesteAccessoAtti" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
<%--        $(function onLoad() {
            var url = '<%=RedirUrl %>';

            $('#paga').on('click', function (e) {
                document.location.replace(url);

                e.preventDefault();
            });

            document.location.replace(url);
        });--%>

</script>

    <style>
        .trasferimento .progress {
            margin-bottom: 12px;
        }

        .trasferimento .panel {
            max-width: 60%;
            margin: 0 auto;
            margin-top: 50px;
        }

        .trasferimento .panel-heading > h3 {
        }

        .trasferimento .btn-primary {
            width: 150px;
            margin-top: 24px;
        }

            .trasferimento .btn-primary:last-child {
                margin-left: 24px;
            }
    </style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">

    <div class="trasferimento">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Ricercare i numeri di protocollo?</h3>
            </div>
            <div class="panel-body">
                Hai bisogno di effettuare una ricerca per individuare i numeri di protocollo di cui chiedere l’accesso agli atti?
                <br />
                <ul>
                    <li>Seleziona <b>"Si"</b> se vuoi effettuare una nuova ricerca o se vuoi allegare una ricerca già predisposta in BDEP (andando nella sezione "le mie ricerche" e selezionando il pulsante <b>"Invia all'istanza"</b>).                                            
                    </li>
                    <li>Seleziona <b>"No"</b> se non è di interesse farlo o se lo si vorrà fare in un momento successivo.</li>
                </ul>


                <div style="width: 100%; text-align: center">
                    <a href="<%=RedirUrl %>" class="btn btn-primary">Si</a>
                    <asp:Button runat="server" ID="cmdNext" CssClass="btn btn-primary" OnClick="cmdNext_Click" Text="No" />
                </div>

            </div>
        </div>

    </div>
</asp:Content>
