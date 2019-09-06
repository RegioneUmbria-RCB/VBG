<%@ Page Title="" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="vbg-istanze-in-sospeso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.vbg_istanze_in_sospeso" %>
<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    <script type="text/javascript">
        $(function onLoad() {
            var url = '<%=RedirUrl %>';

            $('#paga').on('click', function (e) {
                document.location.replace(url);

                e.preventDefault();
            });

            document.location.replace(url);
        });

    </script>

    <style>
        .trasferimento .progress {
            margin-bottom: 12px;
        }

        .trasferimento .panel {
            max-width: 60%;
            margin: 0 auto;
        }

        .trasferimento .panel-heading>h3 {
            font-weight: bold;
        }

        .trasferimento .panel-body {
            line-height: 2em;
        }
    </style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <div class="trasferimento">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Trasferimento in corso</h3>
            </div>
            <div class="panel-body">

                <div class="progress progress-striped active">
                    <div class="progress-bar" style="width: 100%"></div>
                </div>

                In pochi secondi verrai automaticamente trasferito alla pagina richiesta.<br />
                Fai click su <a href="<%=RedirUrl %>" id="paga">questo link</a> se non vuoi attendere
            </div>
        </div>

    </div>
</asp:Content>
