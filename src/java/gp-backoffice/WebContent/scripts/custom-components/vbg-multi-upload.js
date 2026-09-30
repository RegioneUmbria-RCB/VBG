import { copiaStiliDaDocument } from "./copia-stili-da-document.js";


class VbgMultiUpload extends HTMLElement {
	
        templateContent = `

            <style>
				
				.readonly {
					background-color: var(--table-hover-color);
				}
				
				.multi-upload.readonly>.btn-primary {
					display: none;
				}

				.readonly .fa-trash {
					display: none;
				}

				.multi-upload {
					display: inline-block;
					width: 500px;
				}
            	.multi-upload>ul{
            	    margin: 0;
				    padding: 0;
				    list-style-type: none;
				   }
				.multi-upload>ul>li{
					border: 1px solid var(--color-border);
					border-bottom: 0px;
				}
				.multi-upload>ul>li:last-child{
					border-bottom: 1px solid var(--color-border);;
				}
				   
				.dati-file {
					
				    padding: var(--default-padding);
				}
				.multi-upload> .btn-primary {
					display: block;
					margin-right: 0px;
					margin-bottom: var(--half-padding);
				}
				.multi-upload>ul .fa{
					font-size: 1.5em;
				    padding-left: var(--half-padding);
				    color: var(--color-border);
				    cursor: pointer;
				}
				.multi-upload>ul .fa:hover{
				    color: var(--text-color);
				}
				.vbg-progress {
                    width: 100%;
                    margin-top: var(--default-padding);
                    margin-bottom: var(--default-padding);
                }
                
                .vbg-progress > div {
                    background-color: #eee;
                    box-shadow: inset 0 1px 3px rgba(0,0,0,.2);
                    position: relative;
                    overflow: hidden;
                    width: 100%;
                } 
                
                @keyframes cssProgressActive {
                 0% {
                  background-position:0 0
                 }
                 100% {
                  background-position:35px 35px
                 }
                }
                
                .vbg-progress .vbg-progress-bar {
                    height: 18px;
                    
                    display: block;
                    height: 100%;
                    background: #3798d9;
                    background-image: none;
                    background-size: auto;
                    box-shadow: inset 0 -1px 2px rgba(0,0,0,.1);
                    transition: width .8s ease-in-out;
                    background-image: linear-gradient(-45deg,rgba(255,255,255,0.125) 25%,transparent 25%,transparent 50%,rgba(255,255,255,0.125) 50%,rgba(255,255,255,0.125) 75%,transparent 75%,transparent);
                    background-size: 35px 35px;
                    animation: cssProgressActive 2s linear infinite;
                }
            </style>

			<div class="multi-upload" id="multiUpload">
				<div class="btn btn-primary">Scegli file</div>
				<ul>
				</ul>
				<slot></slot>
			</div>
			<input type="file" style="display: none" />
			<vbg-modal id="vbgmodal">
				<div slot='body'>
					<h1>
                        Invio dati in corso...
                    </h1>
                    <p>
                        L'operazione potrebbe richiedere anche alcuni minuti, si prega di attendere senza effettuare altre operazioni
                    </p>
                    <div class="vbg-progress">
                      <div>
                        <div class="vbg-progress-bar cssProgress-active"> 
                          <span class="cssProgress-label">&nbsp;</span> 
                        </div>
                      </div>
                    </div>
				</div>
			</vbg-modal>
        `;

        constructor() {
            super();

            const template = document.createElement('template');
            template.innerHTML = this.templateContent;

            this.attachShadow({ mode: 'open' });
            this.shadowRoot.appendChild(template.content.cloneNode(true));
            this.bottone = this.shadowRoot.querySelector('.btn-primary');
            this.ul = this.shadowRoot.querySelector('ul');

			this.fileUpload = this.shadowRoot.querySelector('input[type=file]');

			this.multiUpload = this.shadowRoot.getElementById('multiUpload');

			this.vbgmodal = this.shadowRoot.querySelector('#vbgmodal');
			
			copiaStiliDaDocument(this.shadowRoot);
        }

        connectedCallback() {
	
				
				this.id = this.getAttribute('id');
	            this.name = this.getAttribute('path-name');
				this.urlVisualizzaFile = this.getAttribute('context-path') + '/file/ajaxDownload.htm';
				this.urlServiziJson = this.getAttribute('context-path') + '/oggettijson/index.htm';
											
				
				if( this.hasAttribute('readonly') ){
					this.multiUpload.classList.add('readonly');
				}
	
	            this.removeAttribute('name');
	
	            this.bottone.addEventListener('click', () => {
	                
					this.fileUpload.click();				
	
	            });
	
				this.fileUpload.addEventListener('change', async () => {
	                
					if( this.fileUpload.files.length === 0 ) {
						return;
					}
					
					const datiFile = await this.uploadFile( this.fileUpload.files[0] );
					
					this.aggiungiFile(datiFile);
					
					this.fileUpload.value = '';
	
	            });
	
	            const slottedInput = this.shadowRoot.querySelector('slot').assignedElements();
	
	            slottedInput.forEach(async (element) => {
	               
					const datiFile = await this.getInfo(element.value);
					
					if( datiFile == null ){
						return;
					}
					
					this.aggiungiFile(datiFile);
	            });

        }

        aggiungiFile = (datiFile) => {
            const li = document.createElement('li');
            //li.innerText = `File_${id}.txt`;
            li.dataset.id = datiFile.id.toString();
            

			const divFileInfo = `
				<div class="dati-file">
					<div class="nome-file" style="display: inline-block;">${datiFile.nome}</div>
						<div class="azioni" style="float: right;">
					    	<i class="fa fa-download"></i>
					        <i class="fa fa-trash"></i>
					    </div>
					</div>
				</div>
			`;			
			li.innerHTML = divFileInfo;
			
            this.ul.appendChild(li);

			const elimina = li.querySelector('.fa-trash');
			elimina.addEventListener('click', () => {
                this.eliminaFile(li);
            });

			const download = li.querySelector('.fa-download');
			download.addEventListener('click', () => {
                this.scaricaFile(li);
            });

            this.creaInputNascosto(li.dataset.id)
        }

		uploadFile = async (file) => {
			
			const formData = new FormData();
			formData.append('fileUpload', file);
			
			console.log(this.urlServiziJson);
			
			const response = await fetch(this.urlServiziJson, {
			    method: 'POST',
			    body: formData
			  });
			
			const oggetto = await response.json();
			
			let retVal =
			{
				id: oggetto.id,
				nome: oggetto.nomeFile,
				url: oggetto.downloadUrl
			};
			
			return retVal;
		}
		
		getInfo = async (id) => {
			
			try{
				
				this.vbgmodal.show();
				
				const response = await fetch(this.urlServiziJson + '?id=' + id, {
				    method: 'GET',
				});
				
				if( response.status != 200 ) {
					const errore = await response.json();
					console.log(errore);
					alert(errore.error);
					return;
				}
				
				const oggetto = await response.json();
				
				let retVal =
				{
					id: oggetto.id,
					nome: oggetto.nomeFile,
					url: oggetto.downloadUrl
				};
				
				return retVal;
			} finally {
				this.vbgmodal.hide();
			}			

		}
		
		delete = async (id) => {
			const response = await fetch(this.urlServiziJson + '?id=' + id, {
			    method: 'DELETE',
			  });
	
			if( response.status != 200 ) {
				const errore = await response.json();
				console.error(errore);
				throw errore.error;
			}
		}

        creaInputNascosto = (fileId) => {
            const hiddenInput = document.createElement('input');
            hiddenInput.setAttribute('type', 'hidden');
            hiddenInput.setAttribute('name', this.name);
            hiddenInput.setAttribute('id', `${this.id}_hidden${fileId}`);
            hiddenInput.value = fileId;

            document.forms[0].appendChild(hiddenInput);
        }

        eliminaInputNascosto = (fileId) => {
            const hiddenInput = document.forms[0].querySelector(`#${this.id}_hidden${fileId}`);

            if (hiddenInput) {
                document.forms[0].removeChild(hiddenInput);
            }
        }

        eliminaFile = async (element) => {
            const id = element.dataset.id;

			await this.delete(id);

            this.eliminaInputNascosto(id);

            this.ul.removeChild(element);
        }

		scaricaFile = (element) => {
			const url = this.urlVisualizzaFile + '?fileId=' + element.dataset.id;
			console.log(url);
			window.open(url);
		}

    }

    customElements.define('vbg-multi-upload', VbgMultiUpload);