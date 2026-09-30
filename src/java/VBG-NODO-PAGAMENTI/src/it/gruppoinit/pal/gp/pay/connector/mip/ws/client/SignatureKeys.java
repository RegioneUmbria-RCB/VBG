package it.gruppoinit.pal.gp.pay.connector.mip.ws.client;

/**
 * Questa classe contiene, per comodità, le due firme, privata e pubblica, con cui comporre il JWT
 * 
 * E' fondamentale aggiornarla, non usando i valori di default
 */
public final class SignatureKeys {

    //Inserire la chiave pubblica con tutta la catena dei certificati
    public final static String PUBLIC = "-----BEGIN CERTIFICATE-----\r\n" +
	    "MIIEXDCCA0SgAwIBAgIIFAsPLlf1lWUwDQYJKoZIhvcNAQELBQAwSzEhMB8GA1UE" +
	    "AwwYTGlndXJpYSBEaWdpdGFsZSBDT0xMIENBMRkwFwYDVQQKDBBMaWd1cmlhIERp" +
	    "Z2l0YWxlMQswCQYDVQQGEwJJVDAeFw0xODEyMTAxMDQxMDZaFw0yMDEyMDkxMDQx" +
	    "MDZaMFAxEzARBgNVBAMMCnV0ZW50ZWNvbGwxETAPBgNVBAsMCENvbGxhdWRvMRkw" +
	    "FwYDVQQKDBBMaWd1cmlhIERpZ2l0YWxlMQswCQYDVQQGEwJJVDCCASIwDQYJKoZI" +
	    "hvcNAQEBBQADggEPADCCAQoCggEBAMnq6bHQwNK43e0nDF7WfHSy8tgDzmFWupcp" +
	    "M/p1QPWHGiV523qQ4ePn00BPmKHDW0VLPGt6dgsc3UBI8tH0FqGRhSGgJKqAbw7O" +
	    "yvcmkOT+ILGeSyVthrP114FkQfGeTfPCijDiiCRl3iwFwkZTIN4Ay+bIXW4V5A+W" +
	    "DTpkO/XxrnLPDp3IeMDi7eCbaXsyHlw+W3U6LlKqAgmr/AL8xIQE1pjoby4FjXhn" +
	    "tAZXFfQH43VuZFuNR2/etv9R1foe9grciSGPgUWr5ujJSM+3q4cCk5z9qtlJLl3J" +
	    "o7CjOP5QZ3bSXrJeQazYgSzAgPHND6Avhy0N9PeyuUDUEsU1lo0CAwEAAaOCAT0w" +
	    "ggE5MAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUoDgFRJVmiIIhhF1WA8IgYfzV" +
	    "kuAwfgYDVR0gBHcwdTBzBggDAQEAAQkIAjBnMGUGCCsGAQUFBwICMFkMV0wndXRp" +
	    "bGl6em8gZGVsIGNlcnRpZmljYXRvIMOoIGxpbWl0YXRvIGFsIHNvbG8gYW1iaWVu" +
	    "dGUgZGkgQ3J5cHRvQXBpIGRpIExpZ3VyaWFEaWdpdGFsZTATBgNVHSUEDDAKBggr" +
	    "BgEFBQcDAjBEBgNVHR8EPTA7MDmgN6A1hjNodHRwOi8vY2F0ZXN0LmxpZ3VyaWFk" +
	    "aWdpdGFsZS5pdC9jcmwvbGlnZGlnY29sbC5jcmwwHQYDVR0OBBYEFKZ/ug6sK+w8" +
	    "xvBYlTkr3XgA0rGkMA4GA1UdDwEB/wQEAwIFoDANBgkqhkiG9w0BAQsFAAOCAQEA" +
	    "qhqQWnzm3LOzFMxfIZLqj4ieN0Tj9W501RTpJVhKAlV3xou3JAtZqqzTVzOPl9h8" +
	    "Qxbr2C8yhW97rOnStvJU059SokbifLjq6CdBQUxXbnlWGy3ZPPKwsGkrv9xUP6on" +
	    "K703a5su8U7vfJ8VF1d0Q2QBU6aUCx/XTumNkVWUHyiMe4/NzD8f2s2ElM6K+R/m" +
	    "41Vy7ytzGDsI6PIQ2Iq5bmwh1sdULvkvXXNqcF+0oiINIuPrAlpvFjSkVKZVRiot" +
	    "5Kyi8wjXYQw7kn5JAv8DukvCwrVIowyD9tO9xQTXtY7ZXlwE2VUQOCNZXvJAo6cX" +
	    "6pXGtbc1UO58HaLtxgu5Cg==" +
	    "-----END CERTIFICATE-----";
    //Inserire chiave privata in formato PCKS8, aggiornare la visibilità del campo
    public final static String PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJ6umx0MDSuN3t" +
	    "Jwxe1nx0svLYA85hVrqXKTP6dUD1hxoledt6kOHj59NAT5ihw1tFSzxrenYLHN1A" +
	    "SPLR9BahkYUhoCSqgG8Ozsr3JpDk/iCxnkslbYaz9deBZEHxnk3zwoow4ogkZd4s" +
	    "BcJGUyDeAMvmyF1uFeQPlg06ZDv18a5yzw6dyHjA4u3gm2l7Mh5cPlt1Oi5SqgIJ" +
	    "q/wC/MSEBNaY6G8uBY14Z7QGVxX0B+N1bmRbjUdv3rb/UdX6HvYK3Ikhj4FFq+bo" +
	    "yUjPt6uHApOc/arZSS5dyaOwozj+UGd20l6yXkGs2IEswIDxzQ+gL4ctDfT3srlA" +
	    "1BLFNZaNAgMBAAECggEAY1MJLg0QLRf/Ix9oOGatxgIY7yXAKaWuF5mPFg8Dq0OQ" +
	    "Gws8aahHVgK9qg79d+VPSmDeEciltIW4WF0KBTlawJOCt629G5oeB1y1/qmb8OkJ" +
	    "UBYbxQeBkZjHL8EPpzlGAziZHb7xVWY+yczzagCOVGZz5sx9GR/vlbMsQqL309z1" +
	    "P1nGr3TqpnSxQDgX9soewbmD8djOVzDWyF8nAlKaULS8Sp0tvUQPYv7MmXwoCx2v" +
	    "xU+/RVFAYgmJ8fdzgQR7UutVRdFt/YPRv047ZTn7wD5lsHFvPxc8BfOPZtniunCZ" +
	    "+CCMvyqQsmIrV8HOOD+GTXyGBCZ2A8hhVwRJ8Xp1CQKBgQDpdhB/1vB3dzUH+yL0" +
	    "70b+cP4AWxioCTBN0ZzCSVlQMlKV1cn9CPD4yk79c9UEX7Jg6OY7+Gd6XpCmO7V8" +
	    "T0fw2iPmfx0cxMj7KpDn+IQVd/X8f11ZjncG/dTA5MOep/c7Sg1qR8azhgQWkY5w" +
	    "3H7K+kqHXu4d9yO3U1n+4nIpnwKBgQDdaUHSN5/IRt+YQi2iX+i66VPYdnpl52U7" +
	    "gdDAR9UAMkH53uJ5HQu2qK3pJ2D5fg3L1f1IMLPh+UrLQMfWzARsgbauJ9lbOI/V" +
	    "Lo2MF16F46SQhKCO6OfCnFvSTscNhqaDmU1PQ1JTxzrXQTcxXwgMzJ+15sTuQFZM" +
	    "4QdGnUboUwKBgH43/qOfKVLteBZgiH2z/8YAgNLgbWwry5nHAeFolojtLAkmFJZh" +
	    "Byb6+cm9niVHN8F9KTyzB/74sWuuTGhw1Iw6473ya2LqYV6pnL5NddvFh5CDq4yH" +
	    "oYJ+KVBAXiKg3OGJH8eeFqdohT1OZJxQGZzTxQd7I3G/8+dhgP50nxQxAoGBALVN" +
	    "eKGL/OIB7xv9rOKWiLttTsQlGEsTtRiKT4b0Cx4TCWVztp2YRDw5Wdde8JC8QM3W" +
	    "F4Yio1n6Sd2v2TQxcbnsacuoQ9rnsRfO/AdoJ3WZl+rjP5pma0k3whSvf127EeUX" +
	    "BnBOXz7NGIilFW3vNGdOs8U/B6lUs5ZWeh+/sA2TAoGBAIdtnKu0SUU5V1mV9iDx" +
	    "TQULaBR2GgwnYYvSJaiUUac2OT331YI3DMTc5IIGhFSP8Rpuc/ROFF4FhrX5yueP" +
	    "YhvtoeTrh4xf7kD1xv+acXTxkt4HTbJ0NJ3IpvhqVZVL5LwF1ZZbz6R22t0rBvoZ" +
	    "qdISWvQH/v2XSUPdel16mUYO";
}
