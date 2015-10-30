module percfill

  use uni
  implicit none

  private
  public :: perc_fill

contains

  !
  ! Function to fill the percolation map with density rho.
  !
  ! All boundary points should be set to zero.
  !
  ! The interior n*n entries should each be set to zero with
  ! probability rho, otherwise should be set to one.
  !

  subroutine perc_fill(n, percmap, rho)

    integer, intent(in)                             :: n
    integer, dimension(0:n+1, 0:n+1), intent(inout) :: percmap
    real, intent(in)                                :: rho

    integer :: i, j

    do i = 0, n+1
       do j = 0, n+1

          percmap(i,j) = mod(i+j,2)

       end do
    end do

  end subroutine perc_fill

end module percfill
