const url = `http://localhost:8080/memo`;
const form = document.querySelector("#modify-form");

// modify(put)
form.addEventListener("submit", (e) => {
  e.preventDefault();

  const send = { mno: form.mno.value, memoText: form.memoText.value };

  console.log(send);

  // post(put)
  fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json", // text/html;charset=UTF-8 -> text/html이랑 바뀌는거임
    },
    body: JSON.stringify(send),
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
          title: "데이터 수정 완료",
          icon: "success",
          draggable: true,
        });
      }
      // 새로고침이래
      // location.reload();
    })
    .catch((err) => console.log(err));
});

// delete
document.querySelector(".btn-outline-danger").addEventListener("click", (e) => {
  const mno = form.mno.value;

  fetch(`http://localhost:8080/memo/${mno}`, {
    method: "DELETE",
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`error! ${res.status}`);
      }

      return res.text();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        Swal.fire({
          title: "데이터 삭제 완료",
          icon: "success",
          draggable: true,
        });
      }
      // 페이지 이동
      location.href = "/memo/list2";
    })
    .catch((err) => console.log(err));
});
