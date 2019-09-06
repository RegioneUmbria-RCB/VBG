using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.DTO.Modulistica;
using Init.SIGePro.Manager.Manager;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneModulistica
{
    public class ModulisticaService
    {
        AuthenticationInfo _authInfo;

        public ModulisticaService(AuthenticationInfo authInfo)
        {
            this._authInfo = authInfo;
        }

        public IEnumerable<CategoriaModulisticaDto> GetModulisticaFrontoffice(string software)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var mgr = new ModelliMgr(db, this._authInfo.IdComune);

                return mgr.GetModulisticaFrontoffice(software);
            }
        }
    }
}
