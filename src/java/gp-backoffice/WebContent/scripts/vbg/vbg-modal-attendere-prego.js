(() => {

    const initializeAttenderePrego = () => {
        const template = `
        <style>
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
                
                .vbg-modal-body p {
                    font-size: 1.2em !important;
                }
            </style>
    
            <div id="modal-attendere-prego" class='vbg-modal'>
                <div class='vbg-modal-body'>
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
            </div>
        `;

        let elemento = document.getElementById('modal-attendere-prego');

        if (!elemento) {
            const e = document.createElement('div');
            e.innerHTML = template;
            document.body.appendChild(e);
            elemento = document.getElementById('modal-attendere-prego');
        }

        window.vbg.mostraModalCaricamento = () => {
            elemento.show();
        }

        window.vbg.nascondiModalCaricamento = () => {
            elemento.hide();
        }
    };

    window.vbg = {
        ...window.vbg || {},
        initializeAttenderePrego: initializeAttenderePrego
    };
})();