using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.AcessibilityControls
{
    public class Enumerators
    {
        public enum Size
        {
            X_SMALL,
            SMALL,
            MEDIUM,
            LARGE,
            X_LARGE,
            XX_LARGE
        }

        public static string GetSizeClass(Size size)
        {
            string s = string.Empty;

            switch (size)
            {
                case Size.X_SMALL:
                    s = "size-xs";
                    break;

                case Size.SMALL:
                    s = "size-sm";
                    break;

                case Size.MEDIUM:
                    s = "size-md";
                    break;

                case Size.LARGE:
                    s = "size-lg";
                    break;

                case Size.X_LARGE:
                    s = "size-xl";
                    break;

                case Size.XX_LARGE:
                    s = "size-xxl";
                    break;

                default:
                    s = "size-md";
                    break;

            }

            return s;
        }

        public enum Position
        {
            TOP,
            BOTTOM,
            LEFT,
            RIGHT
        }

        public static string GetPositionClass(Position position)
        {
            string s = string.Empty;

            switch (position)
            {
                case Position.TOP:
                    s = "top";
                    break;

                case Position.BOTTOM:
                    s = "bottom";
                    break;

                case Position.LEFT:
                    s = "left";
                    break;

                case Position.RIGHT:
                    s = "right";
                    break;

                default:
                    s = "left";
                    break;

            }

            return s;
        }

        public enum Color
        {
            NONE,
            PRIMARY,
            SECONDARY,
            GREEN,
            ORANGE,
            RED,
        }

        public static string GetBackgroundColor(Color color)
        {
            string s = string.Empty;

            switch (color)
            {
                case Color.PRIMARY:
                    s = "primary";
                    break;

                case Color.SECONDARY:
                    s = "secondary";
                    break;

                case Color.GREEN:
                    s = "green";
                    break;

                case Color.ORANGE:
                    s = "orange";
                    break;

                case Color.RED:
                    s = "red";
                    break;

                default:
                    s = "";
                    break;
            }

            return s;
        }

        public static string GetFillColor(Color color)
        {
            string s = string.Empty;

            switch (color)
            {
                case Color.PRIMARY:
                    s = "primary";
                    break;

                case Color.SECONDARY:
                    s = "secondary";
                    break;

                case Color.GREEN:
                    s = "success";
                    break;

                case Color.ORANGE:
                    s = "warning";
                    break;

                case Color.RED:
                    s = "danger";
                    break;

                default:
                    s = "";
                    break;
            }

            return s;
        }
    }
}
