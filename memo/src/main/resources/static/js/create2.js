const url = `http://localhost:8080/memo`;
const form = document.querySelector("#insert-form");

form.addEventListener("submit", (e) => {
  e.preventDefault();

  // post(put)
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // text/html;charset=UTF-8 -> text/html이랑 바뀌는거임
    },
    body: JSON.stringify({ memoText: e.target.memoText.value }),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`error! ${res.status}`);
      }

      // json body 추출
      return res.json();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        Swal.fire({
          title: "데이터 추가 완료",
          icon: "success",
          draggable: true,
        });
      }
      // 새로고침이래
      // location.reload();
    })
    .catch((err) => console.log(err));
});
