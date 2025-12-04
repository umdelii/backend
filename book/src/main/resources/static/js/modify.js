document.querySelector(".btn-danger").addEventListener("click", () => {
  document.getElementById("form").action = "/book/remove";
  document.getElementById("form").submit();
});
