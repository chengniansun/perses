
import java.lang.annotation.*;
class DuplicateAnnotationValue {
  void method() {
    class Inner<@A(value = 2, value = 1) K> {}
  }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A { int value(); }
