type GridItemProps = {
  title: string;
  height?: number;
  children: React.ReactNode;
};

export function GridItem({ title, children }: GridItemProps) {
  return (
    <div
      className={`flex flex-col items-center justify-top p-4 border border-slate-900/10 bg-white rounded-xl min-h-[400px] shadow-lg text-black w-full`}
    >
      <h3 className="text-2xl font-semibold text-black mb-4">{title}</h3>
      <div className="w-full h-full">{children}</div>
    </div>
  );
}

export function TickerSelector({ title, children }: GridItemProps) {
  return (
    <div
      className={`flex flex-col items-center justify-center p-4 border border-slate-900/10 bg-white rounded-xl min-h-[200px] shadow-lg text-black`}
    >
      <h3 className="text-2xl font-semibold text-black mb-4">{title}</h3>
      {children}
    </div>
  );
}
