namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.CSSSchede
{
    internal static class CSSSchedeCore
    {
        public const string CSS = @"
        <style type=""text/css"" media=""all"">
			body{
				/*font-family: arial, sans-serif;*/
				
				--table-header-border-color: #666;
				--table-row-border-color: #aaa;
				--table-row-padding: 12px;
				
				--default-padding: 12px;
				--half-padding: 12px;
				--double-padding: 24px;
				
				--flow-margin: 12px;
				
				--form-element-border-color: #aaa;
				--form-element-background-color: #eee;
				--form-element-padding: 6px;
			}
		
			#datiDinamici{
			
				.row {
					display: flex;
					flex-direction: row;
					justify-content: space-between;
					align-items: stretch;
					max-width: 100%;
					gap: var(--default-padding);
					
					.col {
						flex: 1 1 0 !important;
					}
				}
			
				.single-line-info {
					/*
					margin-bottom: var(--double-padding);
					margin-top: var(--default-padding);
					*/
					.data-text  {
						font-weight: bold;
						border-bottom: 1px solid var(--table-row-border-color);
						display:block;
						padding: var(--form-element-padding);
						min-height: 1rem;
					}
				}
				
				.form-check {
					label {
						font-weight: bold;
					}
				}
				
				.blocco-multiplo {
					padding: var(--default-padding);
					border: 1px solid var(--table-row-border-color);
					margin-bottom: var(--double-padding);
					margin-top: var(--default-padding);
				}
				
				p {
					margin: 0;
					margin-bottom: var(--default-padding);
				}
				
				p:empty {
					display: none;
				}
			

				
				table{
					width: 100%;
					border-collapse: collapse;
					margin-bottom: var(--flow-margin);
				}
				
				table tr > th {
					border-bottom: 2px solid var(--table-header-border-color);
					padding: var(--table-row-padding);
					
				}
				
				table tr > td {
					border-bottom: 1px solid var(--table-row-border-color);
					padding: var(--table-row-padding);
				}
				
				table td>.card-info>span  {
					border: 0 !important;
					padding: 0 !important;
					background-color: transparent;
				}
				
				.card-info{
					margin-bottom: var(--flow-margin);
				}
				
				.card-info > label {
					font-weight: bold;
					margin-bottom: var(--form-element-padding);
					display: inline-block;
				}
				
				.card-info > span,
				.card-info > a {
					border: 1px solid transparent;
					border-bottom: 1px solid var(--form-element-border-color);
					min-height: var(--default-padding);
					display: block;
					padding: var(--form-element-padding);
					pointer-events: none;
					cursor: default;
					background-color: var(--form-element-background-color);
				}
				
				.card-info > a > button {
					background-color: transparent;
					border: 0;
				}
				
				svg { display: none; }
				
				button {
					pointer-events: none;
					cursor: default;
				}
				
			}
        </style>
";


        public const string CSS_old = @"<style type=""text/css"" media=""all"">
        body{
			font-family: arial, sans-serif;
			
			--table-header-border-color: #666;
			--table-row-border-color: #aaa;
			--table-row-padding: 12px;
			
			--default-padding: 12px;
			--half-padding: 12px;
			
			--flow-margin: 12px;
			
			--form-element-border-color: #aaa;
			--form-element-background-color: #eee;
			--form-element-padding: 4px;
		}
		
		p {
			margin: 0;
			margin-bottom: var(--default-padding);
		}
		
		p:empty {
			display: none;
		}
	

		
		table{
			width: 100%;
			border-collapse: collapse;
			margin-bottom: var(--flow-margin);
		}
		
		table tr > th {
			border-bottom: 2px solid var(--table-header-border-color);
			padding: var(--table-row-padding);
			
		}
		
		table tr > td {
			border-bottom: 1px solid var(--table-row-border-color);
			padding: var(--table-row-padding);
		}
		
		table td>.card-info>span  {
			border: 0 !important;
			padding: 0 !important;
			background-color: transparent;
		}
		
		.card-info{
			margin-bottom: var(--flow-margin);
		}
		
		.card-info > label {
			font-weight: bold;
			margin-bottom: var(--form-element-padding);
			display: inline-block;
		}
		
		.card-info > span,
		.card-info > a {
			border: 1px solid transparent;
			border-bottom: 1px solid var(--form-element-border-color);
			min-height: var(--default-padding);
			display: block;
			padding: var(--form-element-padding);
			pointer-events: none;
			cursor: default;
			background-color: var(--form-element-background-color);
		}
		
		.card-info > a > button {
			background-color: transparent;
			border: 0;
		}
		
		svg { display: none; }
		
		button {
			pointer-events: none;
			cursor: default;
		}
	</style>

        ";
    }
}
