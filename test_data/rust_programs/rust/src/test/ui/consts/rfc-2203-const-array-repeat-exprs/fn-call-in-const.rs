// run-pass

#![allow(unused)]
#![feature(const_in_array_repeat_expressions)]

// Some type that is not copyable.
struct Bar;

const fn type_no_copy() -> Option<Bar> {
    None
}

const fn type_copy() -> u32 {
    3
}

const _: [u32; 2] = [type_copy(); 2];

// This is allowed because all promotion contexts use the explicit rules for promotability when
// inside an explicit const context.
const _: [Option<Bar>; 2] = [type_no_copy(); 2];

fn main() {}
