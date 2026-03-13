import com.mini.controller.HelloController;
import com.mini.annotation.Component;

public class AnnotationCheck {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = HelloController.class;
        System.out.println("Class: " + clazz.getName());
        System.out.println("Is annotation: " + clazz.isAnnotation());
        System.out.println("Has Component annotation: " + clazz.isAnnotationPresent(Component.class));
        System.out.println("Is interface: " + clazz.isInterface());

        Component component = clazz.getAnnotation(Component.class);
        if (component != null) {
            System.out.println("Component value: " + component.value());
        }
    }
}