export default function Footer() {
  return (
    <footer
      className={`text-black/70 bg-gray-100 py-6 shadow-md border-t border-gray-300 mt-6`}
    >
      <div className="flex flex-col items-center space-y-2">
        <p className="text-sm">
          Built by{" "}
          <a
            href="https://osmanb.dev"
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-500 hover:underline"
          >
            Osman B
          </a>
        </p>
        <p className="text-xs">
          © {new Date().getFullYear()} Osman B. All rights reserved.
        </p>
      </div>
    </footer>
  );
}
