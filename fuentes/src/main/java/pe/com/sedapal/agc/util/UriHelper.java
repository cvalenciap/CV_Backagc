package pe.com.sedapal.agc.util;

public class UriHelper {

    public static String getResource(String resourceRelativePath) {
        String absolutePath = UriHelper.class.getClassLoader().getResource(resourceRelativePath).getFile();
        // evaluar si es una ruta Windows ya que agrega un / al inicio
        if (absolutePath.contains(":")) {
            absolutePath = absolutePath.replaceFirst("^/(.:/)", "$1");
        }
        return absolutePath;
    }

}
