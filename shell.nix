
{ pkgs ? import <nixpkgs> {} }:
  pkgs.mkShell {
    nativeBuildInputs = [
      pkgs.clojure
      pkgs.clojure-lsp
      pkgs.clj-kondo
    ];
}
