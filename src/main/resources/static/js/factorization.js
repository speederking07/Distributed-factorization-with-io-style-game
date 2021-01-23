const { greet, compute_perfect_squares, compute_linear_equations } = wasm_bindgen;

async function run() {
    await wasm_bindgen("./js/wasm/factorization_bg.wasm");
    greet("hey");
    //smooth from to to_factor
    console.log(compute_perfect_squares(17, "735", "900", "539873"));
    data = "735;352;1 0 0 0 1 0 0$"+
        "750;22627;0 0 0 0 1 0 1$"+
        "783;73216;1 0 0 0 1 1 0$"+
        "801;101728;1 0 0 0 1 0 0";
    console.log(compute_linear_equations(17, "539873", data));
}

run();