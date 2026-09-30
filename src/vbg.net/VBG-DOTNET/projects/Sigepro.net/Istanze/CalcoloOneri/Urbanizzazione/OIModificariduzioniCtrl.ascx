<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="OIModificariduzioniCtrl.ascx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificariduzioniCtrl" %>

<style>
    #<%=dgCausali.ClientID%> {
        --table-border-color: #cfcfcf;
        --default-cell-padding: 16px;
        margin-bottom: 24px;

        th, td {
            border: 1px solid var(--table-border-color);
            /*border-top: 1px solid transparent;*/
            padding: var(--default-cell-padding);
        }

        td {
            padding: var(--default-cell-padding);
            /* border-bottom: 1px solid transparent;*/
        }

        tr>td:first-child{
            font-weight: bold;
        }

        .percentuale_check {
            border-right: 1px solid transparent !important;
            padding-right: 0;
        }

        .bottone-note {
            padding-left: 8px;
        }
    }

    .controllo-modifica-note {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom:0;
        background-color: var(--modal-backdrop-color);
        z-index: 1000;

        .controls-container {
            padding: 1em;
            padding-top: 0;
            background-color: var(--main-bg-color);
            clear: both; /*????*/
            border: 1px solid var(--text-color);
            width: 500px;
            position: relative;
            display: block;
            content: '';
            margin: 0 auto;
            margin-top: 5em;

            textarea {
                margin-bottom: 1em;
                width: 100%;
            }
        }
    }
</style>

<asp:DataGrid runat="server" ID="dgCausali" UseAccessibleHeader="true" DataKeyField="idCausale" OnItemDataBound="dgCausali_ItemDataBound" EnableViewState="False">
</asp:DataGrid>
