

class LambdaScope05 {
    interface VoidFun1 {
        void m(int i);
    }

    static Runnable r1 = () -> { VoidFun1 p = p -> { }; };
    Runnable r2 = () -> { VoidFun1 p = p -> { }; };

    static {
        Runnable r = () -> { VoidFun1 p = p -> { }; };
    }

    {
        Runnable r = () -> { VoidFun1 p = p -> { }; };
    }

    static void m_static() {
        Runnable r = () -> { VoidFun1 p = p -> { }; };
    }

    void m() {
        Runnable r = () -> { VoidFun1 p = p -> { }; };
    }
}
