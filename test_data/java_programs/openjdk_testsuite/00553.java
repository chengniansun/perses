

public interface VariableForm<E extends Element<E, P, V>,
                              P extends PlainForm<E, P, V>,
                              V extends VariableForm<E, P, V>>
    extends Element<E, P, V>
{
}
