load harness

@test "self-1" {
  check '2 % 3 % 4 * 23 + 12 + 3 % 3' '58'
}

@test "self-2" {
  check '-2 + 3 % 4 + 6 * 2 % 2' '1'
}

@test "self-3" {
  check '3 / 3 % 9 * 10' '10'
}

@test "self-4" {
  check '5 % 6 + -9 + 9' '5'
}

@test "self-5" {
  check '5 * 8 % 6 * -4 + -2' '-18'
}

