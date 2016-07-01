using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Bari.IMU;
using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;

namespace Init.Sigepro.FrontEnd.Bari.Tests.IMU
{
    public class SpecificationsTests
    {
        [TestClass]
        public class SelezionateMaxUnaPertinenzaPerCategoriaCatastaleTests
        {
            [TestMethod]
            public void Selezionando_piu_pertinenze_con_stessa_categoria_catastale_restituisce_false()
            {
                var items = new[]{
                    new ImmobileImuDto{
                        CategoriaCatastale = "C2",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },
                    new ImmobileImuDto{
                        CategoriaCatastale = "C6",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },
                    new ImmobileImuDto{
                        CategoriaCatastale = "C7",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },
                    new ImmobileImuDto{
                        CategoriaCatastale = "C2",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },

                };

                var spec = new SelezionateMaxUnaPertinenzaPerCategoriaCatastale();

                var result = spec.IsSatisfiedBy(items);

                Assert.IsFalse(result);
            }

            [TestMethod]
            public void Selezionando_piu_pertinenze_con_stessa_categoria_catastale_vengono_riportate_le_categorie_multiple()
            {
                var items = new[]{
                    new ImmobileImuDto{
                        CategoriaCatastale = "C2",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },
                    new ImmobileImuDto{
                        CategoriaCatastale = "C6",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },
                    new ImmobileImuDto{
                        CategoriaCatastale = "C7",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },
                    new ImmobileImuDto{
                        CategoriaCatastale = "C2",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },

                };

                var spec = new SelezionateMaxUnaPertinenzaPerCategoriaCatastale();

                var result = spec.IsSatisfiedBy(items);

                Assert.AreEqual<int>(1,spec.PertinenzeMultiple.Count());
                Assert.AreEqual<string>("C2", spec.PertinenzeMultiple.ElementAt(0));
            }


            [TestMethod]
            public void Selezionando_piu_pertinenze_con_diversa_categoria_catastale_restituisce_true()
            {
                var items = new[]{
                    new ImmobileImuDto{
                        CategoriaCatastale = "C2",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },
                    new ImmobileImuDto{
                        CategoriaCatastale = "C6",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    },
                    new ImmobileImuDto{
                        CategoriaCatastale = "C7",
                        TipoImmobile = TipoImmobileEnum.Pertinenza
                    }
                };

                var spec = new SelezionateMaxUnaPertinenzaPerCategoriaCatastale();

                var result = spec.IsSatisfiedBy(items);

                Assert.IsTrue(result);
            }
        }
    }
}
